package com.adnstyle.esg_agent.core.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyDetailEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false, unique = true)
    private CompanyEntity company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_size_id")
    private EmployeeSizeRangeEntity employeeSizeRange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revenue_range_id")
    private RevenueRangeEntity revenueRange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_classification_id")
    private IndustryClassificationEntity industryClassification;

    @Builder
    public CompanyDetailEntity(CompanyEntity company, EmployeeSizeRangeEntity employeeSizeRange,
                               RevenueRangeEntity revenueRange, IndustryClassificationEntity industryClassification) {
        this.company = company;
        this.employeeSizeRange = employeeSizeRange;
        this.revenueRange = revenueRange;
        this.industryClassification = industryClassification;
    }

    public void updateDetails(EmployeeSizeRangeEntity employeeSizeRange, RevenueRangeEntity revenueRange,
                              IndustryClassificationEntity industryClassification) {
        this.employeeSizeRange = employeeSizeRange;
        this.revenueRange = revenueRange;
        this.industryClassification = industryClassification;
    }
}