package com.adnstyle.esg_agent.core.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "approval_lines", indexes = {
        @Index(name = "idx_company_id_order", columnList = "company_id, approval_order")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApprovalLineEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private Integer approvalOrder;

    @Builder
    public ApprovalLineEntity(CompanyEntity company, UserEntity user, Integer approvalOrder) {
        this.company = company;
        this.user = user;
        this.approvalOrder = approvalOrder;
    }
}