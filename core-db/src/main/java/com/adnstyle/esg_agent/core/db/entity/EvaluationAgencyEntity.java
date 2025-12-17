package com.adnstyle.esg_agent.core.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evaluation_agencies", indexes = {
        @Index(name = "idx_agency_code", columnList = "agency_code", unique = true)
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluationAgencyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String agencyName;

    @Column(nullable = false, length = 50, unique = true)
    private String agencyCode;

    @OneToMany(mappedBy = "evaluationAgency", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EsgRatingEntity> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "evaluationAgency")
    private List<EsgReportEntity> reports = new ArrayList<>();

    @Builder
    public EvaluationAgencyEntity(String agencyName, String agencyCode) {
        this.agencyName = agencyName;
        this.agencyCode = agencyCode;
    }
}