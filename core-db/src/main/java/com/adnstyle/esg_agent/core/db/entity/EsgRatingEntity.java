package com.adnstyle.esg_agent.core.db.entity;                  // 엔티티 패키지

import jakarta.persistence.*;                                   // JPA 임포트
import lombok.AccessLevel;                                      // Lombok 접근 제어
import lombok.Builder;                                          // Lombok 빌더
import lombok.Getter;                                           // Lombok getter
import lombok.NoArgsConstructor;                                // Lombok 기본 생성자

import java.util.ArrayList;                                     // List 구현체
import java.util.List;                                          // List 인터페이스

/**
 * ESG 등급 마스터 엔티티.
 * - 특정 평가기관이 정의한 등급(예: A, B, C, D 등).
 */
@Entity                                                         // JPA 엔티티
@Table(
        name = "esg_ratings",                                   // 테이블 이름
        indexes = {
                @Index(name = "idx_esg_rating_agency_code",     // 기관+코드 복합 유니크
                        columnList = "evaluation_agency_id, rating_code",
                        unique = true)
        }
)
@Getter                                                         // getter 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)              // 기본 생성자
public class EsgRatingEntity extends BaseEntity {               // BaseEntity 상속

    @Id                                                         // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // auto increment
    private Long id;                                            // ESG 등급 ID

    @ManyToOne(fetch = FetchType.LAZY)                          // 다대일 – 여러 등급이 한 기관에 속함
    @JoinColumn(name = "evaluation_agency_id",                  // FK
            nullable = false)
    private EvaluationAgencyEntity evaluationAgency;            // 평가 기관

    @Column(name = "rating_code",                               // 등급 코드
            nullable = false,
            length = 50)
    private String ratingCode;                                  // 예: KCGS_A

    @Column(name = "rating_name",                               // 등급 이름
            nullable = false,
            length = 100)
    private String ratingName;                                  // 예: A (우수)

    @Column(name = "rating_level",                              // 등급 레벨
            nullable = false)
    private Integer ratingLevel;                                // 숫자 등급(1=최상, 5=최하 등)

    @OneToMany(mappedBy = "esgRating")                          // 1:N – 보고서에서 참조
    private List<EsgReportEntity> reports                       // 이 등급을 받은 보고서 목록
            = new ArrayList<>();

    @Builder                                                    // 빌더 생성자
    public EsgRatingEntity(EvaluationAgencyEntity evaluationAgency,
                           String ratingCode,
                           String ratingName,
                           Integer ratingLevel) {
        this.evaluationAgency = evaluationAgency;               // 기관 설정
        this.ratingCode = ratingCode;                           // 코드 설정
        this.ratingName = ratingName;                           // 이름 설정
        this.ratingLevel = ratingLevel;                         // 레벨 설정
    }
}
