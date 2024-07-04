package com.adhyayan.service.services;

import com.adhyayan.service.repo.OrganizationRepo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    @Resource
    private OrganizationRepo organizationRepo;


}