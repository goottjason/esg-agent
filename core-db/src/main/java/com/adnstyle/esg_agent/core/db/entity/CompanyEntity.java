package com.adnstyle.esg_agent.core.db.entity;

import com.adnstyle.esg_agent.core.enums.CompanyType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies", indexes = {
        @Index(name = "idx_biz_reg_num", columnList = "biz_reg_num", unique = true),
        @Index(name = "idx_company_type", columnList = "company_type")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String companyName;

    @Column(nullable = false, length = 10, unique = true)
    private String bizRegNum;  // 사업자등록번호

    @Column(nullable = false, length = 50)
    private String ceoName;

    @Column(nullable = false, length = 11)
    private String ceoPhone;

    @Column(nullable = false, length = 5)
    private String zipCode;

    @Column(nullable = false, length = 255)
    private String address1;

    @Column(length = 255)
    private String address2;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CompanyType companyType;

    @Column(length = 13)
    private String corpRegNum;  // 법인등록번호 (법인기업만)

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private CompanyDetailEntity companyDetail;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserEntity> users = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApprovalLineEntity> approvalLines = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EsgReportEntity> esgReports = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyTodoEntity> todos = new ArrayList<>();

    @Builder
    public CompanyEntity(String companyName, String bizRegNum, String ceoName, String ceoPhone,
                         String zipCode, String address1, String address2,
                         CompanyType companyType, String corpRegNum) {
        this.companyName = companyName;
        this.bizRegNum = bizRegNum;
        this.ceoName = ceoName;
        this.ceoPhone = ceoPhone;
        this.zipCode = zipCode;
        this.address1 = address1;
        this.address2 = address2;
        this.companyType = companyType;
        this.corpRegNum = corpRegNum;
    }

    public void updateBasicInfo(String ceoName, String ceoPhone, String address1, String address2, String zipCode) {
        this.ceoName = ceoName;
        this.ceoPhone = ceoPhone;
        this.address1 = address1;
        this.address2 = address2;
        this.zipCode = zipCode;
    }
}