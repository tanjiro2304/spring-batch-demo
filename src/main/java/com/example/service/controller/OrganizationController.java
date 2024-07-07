package com.example.service.controller;

import com.example.objects.dto.OrganizationDto;
import com.example.service.models.Organization;
import com.example.service.repo.OrganizationRepo;
import com.example.service.services.OrganizationService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping(value="/organizations")
public class OrganizationController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationController.class);

    @Resource
    private JobLauncher jobLauncher;

    @Resource
    private Job job;

    @Resource
    private OrganizationService organizationService;


    @PostMapping(value="/import")
    public void importOrganization(){
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        try{
            jobLauncher.run(job, jobParameters);
        }catch (JobExecutionAlreadyRunningException
                | JobRestartException
                | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e){
            log.error(e.getMessage());
        }
    }

//    @GetMapping("/findAll")
//    public ResponseEntity<List<Organization>> findAll(){
//
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//
//        List<Organization> mainList = new ArrayList<>();
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//
//        Future<List<Organization>> submit1 =
//                executorService.submit(() -> organizationRepo.findAll(PageRequest.of(0, 25000)).stream().toList());
//        Future<List<Organization>> submit2 =
//                executorService.submit(() -> organizationRepo.findAll(PageRequest.of(1, 25000)).stream().toList());
//        Future<List<Organization>> submit3 =
//                executorService.submit(() -> organizationRepo.findAll(PageRequest.of(2, 25000)).stream().toList());
//        Future<List<Organization>> submit4 =
//                executorService.submit(() -> organizationRepo.findAll(PageRequest.of(3, 25000)).stream().toList());
//
//        stopWatch.stop();
//        System.out.println("Total Time Taken :- " + stopWatch.getTotalTime(TimeUnit.MINUTES));
//        try {
//            mainList.addAll(submit1.get());
//            mainList.addAll(submit2.get());
//            mainList.addAll(submit3.get());
//            mainList.addAll(submit4.get());
//
//        } catch (InterruptedException | ExecutionException e) {
//            log.error(e.getMessage());
//        }
//        System.out.println("Total Size : "+ mainList.size());
//        return new ResponseEntity<>(mainList, HttpStatus.OK);
//    }

    @GetMapping("/findAllData")
    public ResponseEntity<List<OrganizationDto>> findAllData(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<OrganizationDto> list = organizationService.findAll();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTime(TimeUnit.MINUTES));
        return new ResponseEntity<>(list,HttpStatus.OK);
    } 
}