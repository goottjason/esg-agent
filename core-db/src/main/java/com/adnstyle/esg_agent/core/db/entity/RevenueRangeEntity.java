package com.adnstyle.esg_agent.core.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "revenue_ranges", indexes = {
        @Index(name = "idx_range_code", columnList = "range_code", unique = true)
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RevenueRangeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long minRevenue;

    @Column(nullable = false)
    private Long maxRevenue;

    @Column(nullable = false, length = 50)
    private String rangeName;

    @Column(nullable = false, length = 50)
    private String rangeCode;

    @OneToMany(mappedBy = "revenueRange")
    private List<CompanyDetailEntity> companyDetails = new ArrayList<>();

    @Builder
    public RevenueRangeEntity(Long minRevenue, Long maxRevenue, String rangeName, String rangeCode) {
        this.minRevenue = minRevenue;
        this.maxRevenue = maxRevenue;
        this.rangeName = rangeName;
        this.rangeCode = rangeCode;
    }
}