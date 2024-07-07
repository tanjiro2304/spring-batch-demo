package com.example.service.config;

import com.example.service.models.Organization;
import com.example.service.repo.OrganizationRepo;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor

public class BatchConfig {

    @Resource
    private OrganizationRepo organizationRepo;

    @Resource
    private PlatformTransactionManager platformTransactionManager;

    @Resource
    private JobRepository jobRepository;

    @Bean
    public FlatFileItemReader<Organization> itemReader() {
        FlatFileItemReader<Organization> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/organizations.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    @Bean
    public OrganizationProcessor processor(){
        return new OrganizationProcessor();
    }


    @Bean
    public RepositoryItemWriter<Organization> writer(){
        RepositoryItemWriter<Organization> writer = new RepositoryItemWriter<>();
        writer.setRepository(organizationRepo);
        writer.setMethodName("save");
        return writer;
    }

    private LineMapper<Organization> lineMapper() {
        DefaultLineMapper<Organization> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(Boolean.FALSE);
        lineTokenizer.setNames("index","organizationId","name","country","description",
                "founded","industry","numberOfEmployees");

        BeanWrapperFieldSetMapper<Organization> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Organization.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;

    }

    private Step importStep(){
        return new StepBuilder("csvImport", jobRepository)
                .<Organization, Organization>chunk(100000,platformTransactionManager)
                .processor(processor())
                .reader(itemReader())
                .writer(writer())
                .build();
    }

    @Bean
    public Job runJob(){
        return new JobBuilder("importOrganization", jobRepository)
                .start(importStep())
                .build();
    }

}