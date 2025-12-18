package com.adnstyle.esg_agent.core.db.entity;                  // 엔티티 패키지

import jakarta.persistence.*;                                   // JPA 임포트
import lombok.AccessLevel;                                      // Lombok 접근 제어
import lombok.Builder;                                          // Lombok 빌더
import lombok.Getter;                                           // Lombok getter
import lombok.NoArgsConstructor;                                // Lombok 기본 생성자

import java.util.ArrayList;                                     // List 구현체
import java.util.List;                                          // List 인터페이스

/**
 * 산업 분류 마스터 엔티티.
 * - 자기 자신을 부모로 가지는 트리 구조를 형성할 수 있다.
 */
@Entity                                                         // JPA 엔티티
@Table(
        name = "industry_classifications",                      // 테이블 이름
        indexes = {
                @Index(name = "idx_industry_code",              // 코드 인덱스
                        columnList = "code",
                        unique = true),
                @Index(name = "idx_industry_level",             // 레벨 인덱스
                        columnList = "level")
        }
)
@Getter                                                         // getter 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)              // 기본 생성자
public class IndustryClassificationEntity extends BaseEntity {  // BaseEntity 상속

    @Id                                                         // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // auto increment
    private Long id;                                            // 산업 분류 ID

    @Column(name = "code",                                     // 코드 컬럼
            nullable = false,
            length = 10,
            unique = true)
    private String code;                                        // 산업 코드

    @Column(name = "name", nullable = false, length = 255)      // 이름 컬럼
    private String name;                                        // 산업 이름

    @Column(name = "level", nullable = false)                   // 레벨 컬럼
    private Integer level;                                      // 트리 레벨(1, 2, 3 ...)

    @ManyToOne(fetch = FetchType.LAZY)                          // 다대일 – 부모 노드
    @JoinColumn(name = "parent_id")                             // 부모 ID FK
    private IndustryClassificationEntity parent;                // 부모 산업 분류

    @OneToMany(                                                 // 1:N – 자식 노드 리스트
            mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<IndustryClassificationEntity> children         // 자식 목록
            = new ArrayList<>();

    @OneToMany(mappedBy = "industryClassification")             // 1:N – 회사 상세에서 참조
    private List<CompanyDetailEntity> companyDetails            // 이 분류를 사용하는 회사 목록
            = new ArrayList<>();

    @Builder                                                    // 빌더 생성자
    public IndustryClassificationEntity(String code,
                                        String name,
                                        Integer level,
                                        IndustryClassificationEntity parent) {
        this.code = code;                                       // 코드 설정
        this.name = name;                                       // 이름 설정
        this.level = level;                                     // 레벨 설정
        this.parent = parent;                                   // 부모 설정
    }

    /**
     * 이름/레벨 변경 메서드.
     */
    public void updateInfo(String name, Integer level) {
        this.name = name;                                       // 이름 수정
        this.level = level;                                     // 레벨 수정
    }
}
