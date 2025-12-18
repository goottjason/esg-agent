package com.adnstyle.esg_agent.core.db.entity;

import com.adnstyle.esg_agent.core.enums.CompanyUserRole;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "company_users",
        indexes = {
                @Index(name = "idx_company_users_company", columnList = "company_id"),
                @Index(name = "idx_company_users_account", columnList = "user_account_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CompanyUserEntity extends BaseEntity {

    // ----- 컬럼
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccountEntity userAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 50)
    private CompanyUserRole role;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "department", length = 50)
    private String department;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "is_default_approver", nullable = false)
    @Builder.Default
    private Boolean defaultApprover = false;

    // ----- 연관관계
    @OneToMany(
            mappedBy = "companyUser", // ApprovalLineEntity.companyUser 필드(company_user_id)에 매핑
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ApprovalLineEntity> approvalLines = new ArrayList<>(); // 이 사용자가 포함된 결재선

    @OneToMany(
            mappedBy = "companyUser" // TodoAssigneeEntity.companyUser에 매핑
    )
    private List<TodoAssigneeEntity> assignedTodos = new ArrayList<>(); // 이 사용자가 담당자로 지정된 TO-DO

    // ----- 도메인 메서드
    public void updateRole(CompanyUserRole role) { this.role = role; }


    public void updateProfile(String title,
                              String department,
                              String phone) {
        this.title = title;
        this.department = department;
        this.phone = phone;
    }

    public void updateDefaultApprover(Boolean defaultApprover) { this.defaultApprover = defaultApprover;}
}
