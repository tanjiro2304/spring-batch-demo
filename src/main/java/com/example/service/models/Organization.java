package com.example.service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="TBL_ORGANIZATION")
public class Organization {
    @Id
    @Column(name="id")
    private String id;

    @Column(name="organization_id")
    private String organizationId;

    @Column
    private String name;

    @Column
    private String country;

    @Column
    private String description;

    @Column
    private String founded;

    @Column
    private String industry;

    @Column(name="number_of_employees")
    private String numberOfEmployees;


}