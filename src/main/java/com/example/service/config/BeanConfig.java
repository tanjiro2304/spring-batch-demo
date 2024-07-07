package com.example.service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}