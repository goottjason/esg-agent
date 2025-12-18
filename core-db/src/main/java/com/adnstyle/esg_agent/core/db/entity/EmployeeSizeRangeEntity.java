package com.adnstyle.esg_agent.core.db.entity;                  // 엔티티 패키지

import jakarta.persistence.*;                                   // JPA 임포트
import lombok.AccessLevel;                                      // Lombok 접근 제어
import lombok.Builder;                                          // Lombok 빌더
import lombok.Getter;                                           // Lombok getter
import lombok.NoArgsConstructor;                                // Lombok 기본 생성자

import java.util.ArrayList;                                     // List 구현체
import java.util.List;                                          // List 인터페이스

/**
 * 직원 수 구간 마스터 엔티티.
 * - 여러 회사 상세 정보에서 참조한다.
 */
@Entity                                                         // JPA 엔티티
@Table(
        name = "employee_size_ranges",                          // 테이블 이름
        indexes = {
                @Index(name = "idx_employee_size_range_code",   // 코드 인덱스
                        columnList = "range_code",
                        unique = true)
        }
)
@Getter                                                         // getter 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)              // 보호 수준 기본 생성자
public class EmployeeSizeRangeEntity extends BaseEntity {       // BaseEntity 상속

    @Id                                                         // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // auto increment
    private Long id;                                            // 직원 수 구간 ID

    @Column(name = "min_employees", nullable = false)           // null 불가
    private Integer minEmployees;                               // 최소 직원 수

    @Column(name = "max_employees", nullable = false)           // null 불가
    private Integer maxEmployees;                               // 최대 직원 수

    @Column(name = "range_name", nullable = false, length = 50) // 표시 이름
    private String rangeName;                                   // 구간 이름(예: 소기업 (1~25인))

    @Column(name = "range_code", nullable = false, length = 50) // 코드
    private String rangeCode;                                   // 구간 코드(예: MICRO)

    @OneToMany(mappedBy = "employeeSizeRange")                  // 1:N – 여러 회사 상세 정보에서 참조
    private List<CompanyDetailEntity> companyDetails            // 참조하는 회사 상세 목록
            = new ArrayList<>();

    @Builder                                                    // 빌더 생성자
    public EmployeeSizeRangeEntity(Integer minEmployees,
                                   Integer maxEmployees,
                                   String rangeName,
                                   String rangeCode) {
        this.minEmployees = minEmployees;                       // 최소 직원 수 설정
        this.maxEmployees = maxEmployees;                       // 최대 직원 수 설정
        this.rangeName = rangeName;                             // 구간 이름 설정
        this.rangeCode = rangeCode;                             // 구간 코드 설정
    }
}
