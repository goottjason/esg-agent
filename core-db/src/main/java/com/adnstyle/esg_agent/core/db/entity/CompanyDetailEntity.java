package com.adnstyle.esg_agent.core.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_details")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CompanyDetailEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false, unique = true)
    private CompanyEntity company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_size_range_id")
    private EmployeeSizeRangeEntity employeeSizeRange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revenue_range_id")
    private RevenueRangeEntity revenueRange;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_classification_id")
    private IndustryClassificationEntity industryClassification;

    // ----- 도메인 메서드
    public void updateDetails(EmployeeSizeRangeEntity employeeSizeRange,
                              RevenueRangeEntity revenueRange,
                              IndustryClassificationEntity industryClassification) {
        this.employeeSizeRange = employeeSizeRange;
        this.revenueRange = revenueRange;
        this.industryClassification = industryClassification;
    }
}
