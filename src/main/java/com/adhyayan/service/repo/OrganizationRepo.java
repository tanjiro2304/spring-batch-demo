package com.adhyayan.service.repo;

import com.adhyayan.service.models.Organization;
import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

@Resource
public interface OrganizationRepo extends JpaRepository<Organization, String> {
}