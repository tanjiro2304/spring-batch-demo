package com.adhyayan.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobCompletionNotificationImpl implements JobExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(JobCompletionNotificationImpl.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Job Started");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("Job Ended");
    }
}
