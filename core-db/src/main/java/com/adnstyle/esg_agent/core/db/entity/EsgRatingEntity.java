package com.adnstyle.esg_agent.core.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "esg_ratings", indexes = {
        @Index(name = "idx_agency_id_code", columnList = "evaluation_agency_id, rating_code", unique = true)
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EsgRatingEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_agency_id", nullable = false)
    private EvaluationAgencyEntity evaluationAgency;

    @Column(nullable = false, length = 50)
    private String ratingCode;

    @Column(nullable = false, length = 100)
    private String ratingName;

    @Column(nullable = false)
    private Integer ratingLevel;

    @OneToMany(mappedBy = "esgRating")
    private List<EsgReportEntity> reports = new ArrayList<>();

    @Builder
    public EsgRatingEntity(EvaluationAgencyEntity evaluationAgency, String ratingCode,
                           String ratingName, Integer ratingLevel) {
        this.evaluationAgency = evaluationAgency;
        this.ratingCode = ratingCode;
        this.ratingName = ratingName;
        this.ratingLevel = ratingLevel;
    }
}