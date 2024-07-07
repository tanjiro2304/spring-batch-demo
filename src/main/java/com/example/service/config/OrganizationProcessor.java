package com.example.service.config;

import com.example.service.models.Organization;
import org.springframework.batch.item.ItemProcessor;

public class OrganizationProcessor implements ItemProcessor<Organization, Organization> {
    @Override
    public Organization process(Organization organization) throws Exception {
        return organization;
    }
}