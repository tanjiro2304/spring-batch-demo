package com.example.service.repo;

import com.example.service.models.Organization;
import jakarta.annotation.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

@Resource
public interface OrganizationRepo extends JpaRepository<Organization, String> {
}