package com.adnstyle.esg_agent.core.db.entity;                  // 엔티티 패키지

import jakarta.persistence.*;                                   // JPA 임포트
import lombok.AccessLevel;                                      // Lombok 접근 제어
import lombok.Builder;                                          // Lombok 빌더
import lombok.Getter;                                           // Lombok getter
import lombok.NoArgsConstructor;                                // Lombok 기본 생성자

import java.util.ArrayList;                                     // List 구현체
import java.util.List;                                          // List 인터페이스

/**
 * 매출 구간 마스터 엔티티.
 * - 여러 회사 상세 정보에서 참조한다.
 */
@Entity                                                         // JPA 엔티티
@Table(
        name = "revenue_ranges",                                // 테이블 이름
        indexes = {
                @Index(name = "idx_revenue_range_code",         // 코드 인덱스
                        columnList = "range_code",
                        unique = true)
        }
)
@Getter                                                         // getter 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)              // 보호 수준 생성자
public class RevenueRangeEntity extends BaseEntity {            // BaseEntity 상속

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "min_revenue", nullable = false)
    private Long minRevenue;

    @Column(name = "max_revenue", nullable = false)
    private Long maxRevenue;

    @Column(name = "range_name", nullable = false, length = 50)
    private String rangeName;

    @Column(name = "range_code", nullable = false, length = 50)
    private String rangeCode;

    @OneToMany(mappedBy = "revenueRange")
    private List<CompanyDetailEntity> companyDetails = new ArrayList<>();

    @Builder                                                    // 빌더 생성자
    public RevenueRangeEntity(Long minRevenue,
                              Long maxRevenue,
                              String rangeName,
                              String rangeCode) {
        this.minRevenue = minRevenue;                           // 최소 매출 설정
        this.maxRevenue = maxRevenue;                           // 최대 매출 설정
        this.rangeName = rangeName;                             // 구간 이름 설정
        this.rangeCode = rangeCode;                             // 코드 설정
    }
}
