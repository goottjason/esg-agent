package com.adnstyle.esg_agent.core.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "esg_reports", indexes = {
        @Index(name = "idx_company_id", columnList = "company_id"),
        @Index(name = "idx_agency_id", columnList = "evaluation_agency_id")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EsgReportEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_agency_id", nullable = false)
    private EvaluationAgencyEntity evaluationAgency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "esg_rating_id")
    private EsgRatingEntity esgRating;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(length = 500)
    private String fileUrl;

    @Builder
    public EsgReportEntity(CompanyEntity company, EvaluationAgencyEntity evaluationAgency,
                           EsgRatingEntity esgRating, LocalDate startDate, LocalDate endDate,
                           String fileUrl) {
        this.company = company;
        this.evaluationAgency = evaluationAgency;
        this.esgRating = esgRating;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fileUrl = fileUrl;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(endDate);
    }
}