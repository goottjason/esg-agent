package com.adnstyle.esg_agent.core.db.entity;               // 엔티티 패키지

import jakarta.persistence.*;                                // JPA 임포트
import lombok.AccessLevel;                                   // Lombok 접근 제어
import lombok.Builder;                                       // Lombok 빌더
import lombok.Getter;                                        // Lombok getter
import lombok.NoArgsConstructor;                             // Lombok 기본 생성자

/**
 * 회사별 기본 결재선 구성 엔티티.
 * - 하나의 회사에 대해 결재 순서대로 CompanyUser를 나열.
 * - 예: 1: 팀장, 2: 본부장, 3: 대표이사
 */
@Entity                                                      // JPA 엔티티
@Table(
        name = "approval_lines",                             // 테이블 이름
        indexes = {
                @Index(
                        name = "idx_approval_lines_company_order",
                        columnList = "company_id, approval_order"
                )
        }
)
@Getter                                                      // getter 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)           // 기본 생성자 보호 수준
public class ApprovalLineEntity extends BaseEntity {         // BaseEntity 상속

    @Id                                                      // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)      // auto increment
    private Long id;                                         // 결재선 항목 ID

    @ManyToOne(fetch = FetchType.LAZY)                      // 다대일 – 여러 결재선 항목이 한 회사에 속함
    @JoinColumn(name = "company_id", nullable = false)      // 회사 ID FK
    private CompanyEntity company;                           // 결재선이 속한 회사

    @ManyToOne(fetch = FetchType.LAZY)                      // 다대일 – 여러 결재선 항목이 한 회사 사용자에 연결
    @JoinColumn(name = "company_user_id", nullable = false) // 회사 사용자 ID FK
    private CompanyUserEntity companyUser;                   // 결재 담당자(CompanyUser)

    @Column(name = "approval_order", nullable = false)       // null 불가
    private Integer approvalOrder;                           // 결재 순서(1, 2, 3 ...)

    @Builder                                                 // 빌더 패턴 생성자
    public ApprovalLineEntity(CompanyEntity company,
                              CompanyUserEntity companyUser,
                              Integer approvalOrder) {
        this.company = company;                              // 회사 설정
        this.companyUser = companyUser;                      // 회사 사용자 설정
        this.approvalOrder = approvalOrder;                  // 결재 순서 설정
    }

    /**
     * 결재 순서 변경.
     */
    public void updateOrder(Integer approvalOrder) {
        this.approvalOrder = approvalOrder;                  // 순서 필드 업데이트
    }
}
