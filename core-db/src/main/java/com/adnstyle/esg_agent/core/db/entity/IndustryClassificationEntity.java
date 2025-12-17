package com.adnstyle.esg_agent.core.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "industry_classifications", indexes = {
        @Index(name = "idx_code", columnList = "code", unique = true),
        @Index(name = "idx_level", columnList = "level")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IndustryClassificationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10, unique = true)
    private String code;  // 산업분류코드

    @Column(nullable = false, length = 255)
    private String name;  // 산업분류명

    @Column(nullable = false)
    private Integer level;  // 계층 (1~5)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private IndustryClassificationEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IndustryClassificationEntity> children = new ArrayList<>();

    @OneToMany(mappedBy = "industryClassification")
    private List<CompanyDetailEntity> companyDetails = new ArrayList<>();

    @Builder
    public IndustryClassificationEntity(String code, String name, Integer level, IndustryClassificationEntity parent) {
        this.code = code;
        this.name = name;
        this.level = level;
        this.parent = parent;
    }
}
