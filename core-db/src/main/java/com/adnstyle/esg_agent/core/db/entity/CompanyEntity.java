package com.adnstyle.esg_agent.core.db.entity;

import com.adnstyle.esg_agent.core.enums.CompanyType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "companies",
        indexes = {
                @Index(name = "idx_companies_biz_reg_num", columnList = "biz_reg_num", unique = true),
                @Index(name = "idx_companies_company_type", columnList = "company_type")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CompanyEntity extends BaseEntity {

    // ----- 컬럼
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "biz_reg_num", nullable = false, length = 10, unique = true)
    private String bizRegNum;

    @Column(name = "ceo_name", nullable = false, length = 50)
    private String ceoName;

    @Column(name = "ceo_phone", nullable = false, length = 11)
    private String ceoPhone;

    @Column(name = "zip_code", nullable = false, length = 5)
    private String zipCode;

    @Column(name = "address1", nullable = false, length = 255)
    private String address1;

    @Column(name = "address2", length = 255)
    private String address2;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type", nullable = false, length = 20)
    private CompanyType companyType;

    @Column(name = "corp_reg_num", length = 13)
    private String corpRegNum;

    // ----- 연관관계
    @OneToOne(
            mappedBy = "company", // CompanyDetailEntity.company 필드(company_id 컬럼)에 의해 매핑
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private CompanyDetailEntity detail;

    @OneToMany(
            mappedBy = "company", // CompanyUserEntity.company 필드(company_id 컬럼)에 의해 매핑
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CompanyUserEntity> companyUsers = new ArrayList<>(); // 회사별 사용자 목록

    @OneToMany(
            mappedBy = "company", // EsgReportEntity.company 필드(company_id 컬럼)에 의해 매핑
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EsgReportEntity> esgReports = new ArrayList<>(); // ESG 평가 리포트 목록

    @OneToMany(
            mappedBy = "company", // CompanyTodoEntity.company 필드(company_id 컬럼)에 의해 매핑
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CompanyTodoEntity> todos = new ArrayList<>(); // 회사별 TO-DO 목록

    // ----- 도메인 메서드
    public void updateBasicInfo(String ceoName,
                                String ceoPhone,
                                String zipCode,
                                String address1,
                                String address2) {
        this.ceoName = ceoName;
        this.ceoPhone = ceoPhone;
        this.zipCode = zipCode;
        this.address1 = address1;
        this.address2 = address2;
    }

    // NOTE: CompanyDetailEntity의 updateDetails와 혼동될 우려
    public void updateDetail(CompanyDetailEntity detail) { this.detail = detail; }
}
