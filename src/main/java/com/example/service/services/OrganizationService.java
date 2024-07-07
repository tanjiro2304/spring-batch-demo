package com.example.service.services;

import com.example.objects.dto.OrganizationDto;
import com.example.service.models.Organization;
import com.example.service.repo.OrganizationRepo;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @Resource
    private OrganizationRepo organizationRepo;

    @Resource
    private ModelMapper mapper;

    public List<OrganizationDto> findAll(){
        return organizationRepo.findAll().stream().map(obj -> mapper.map(obj,
                OrganizationDto.class)).toList();
    }

}