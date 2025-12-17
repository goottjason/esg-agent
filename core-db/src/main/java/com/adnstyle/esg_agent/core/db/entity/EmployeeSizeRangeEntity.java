package com.adnstyle.esg_agent.core.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee_size_ranges", indexes = {
        @Index(name = "idx_range_code", columnList = "range_code", unique = true)
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeSizeRangeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer minEmployees;

    @Column(nullable = false)
    private Integer maxEmployees;

    @Column(nullable = false, length = 50)
    private String rangeName;

    @Column(nullable = false, length = 50)
    private String rangeCode;

    @OneToMany(mappedBy = "employeeSizeRange")
    private List<CompanyDetailEntity> companyDetails = new ArrayList<>();

    @Builder
    public EmployeeSizeRangeEntity(Integer minEmployees, Integer maxEmployees, String rangeName, String rangeCode) {
        this.minEmployees = minEmployees;
        this.maxEmployees = maxEmployees;
        this.rangeName = rangeName;
        this.rangeCode = rangeCode;
    }
}