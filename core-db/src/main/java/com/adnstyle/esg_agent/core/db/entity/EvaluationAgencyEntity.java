package com.adnstyle.esg_agent.core.db.entity;                  // 엔티티 패키지

import jakarta.persistence.*;                                   // JPA 임포트
import lombok.AccessLevel;                                      // Lombok 접근 제어
import lombok.Builder;                                          // Lombok 빌더
import lombok.Getter;                                           // Lombok getter
import lombok.NoArgsConstructor;                                // Lombok 기본 생성자

import java.util.ArrayList;                                     // List 구현체
import java.util.List;                                          // List 인터페이스

/**
 * ESG 평가 기관 마스터 엔티티.
 * - KCGS, MSCI, Sustainalytics 등.
 */
@Entity                                                         // JPA 엔티티
@Table(
        name = "evaluation_agencies",                           // 테이블 이름
        indexes = {
                @Index(name = "idx_evaluation_agency_code",     // 코드 인덱스
                        columnList = "agency_code",
                        unique = true)
        }
)
@Getter                                                         // getter 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)              // 기본 생성자
public class EvaluationAgencyEntity extends BaseEntity {        // BaseEntity 상속

    @Id                                                         // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // auto increment
    private Long id;                                            // 평가 기관 ID

    @Column(name = "agency_name",                               // 기관 이름
            nullable = false,
            length = 100)
    private String agencyName;                                  // 예: 한국기업지배구조원

    @Column(name = "agency_code",                               // 기관 코드
            nullable = false,
            length = 50,
            unique = true)
    private String agencyCode;                                  // 예: KCGS, MSCI

    @OneToMany(
            mappedBy = "evaluationAgency",                      // EsgRatingEntity.evaluationAgency
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EsgRatingEntity> ratings = new ArrayList<>();  // 제공하는 ESG 등급 목록

    @OneToMany(
            mappedBy = "evaluationAgency"                       // EsgReportEntity.evaluationAgency
    )
    private List<EsgReportEntity> reports = new ArrayList<>();  // 이 기관이 평가한 보고서 목록

    @Builder                                                    // 빌더 생성자
    public EvaluationAgencyEntity(String agencyName,
                                  String agencyCode) {
        this.agencyName = agencyName;                           // 이름 설정
        this.agencyCode = agencyCode;                           // 코드 설정
    }
}
