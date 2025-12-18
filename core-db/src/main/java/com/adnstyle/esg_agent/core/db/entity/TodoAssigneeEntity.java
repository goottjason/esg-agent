package com.adnstyle.esg_agent.core.db.entity;               // 엔티티 패키지

import jakarta.persistence.*;                                // JPA 임포트
import lombok.AccessLevel;                                   // Lombok 접근 제어
import lombok.Builder;                                       // Lombok 빌더
import lombok.Getter;                                        // Lombok getter
import lombok.NoArgsConstructor;                             // Lombok 기본 생성자

/**
 * 회사별 TO-DO 항목의 담당자(협업자) 매핑 엔티티.
 * - 하나의 CompanyTodo에 여러 CompanyUser를 할당할 수 있다.
 */
@Entity                                                      // JPA 엔티티
@Table(
        name = "todo_assignees",                             // 테이블 이름
        indexes = {
                @Index(name = "idx_todo_assignees_todo", columnList = "company_todo_id"),
                @Index(name = "idx_todo_assignees_company_user", columnList = "company_user_id")
        }
)
@Getter                                                      // getter 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)           // 기본 생성자 보호 수준
public class TodoAssigneeEntity extends BaseEntity {         // BaseEntity 상속

    @Id                                                      // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)      // auto increment
    private Long id;                                         // TO-DO 담당자 레코드 ID

    @ManyToOne(fetch = FetchType.LAZY)                      // 다대일 – 여러 담당자 레코드가 한 TO-DO에 연결
    @JoinColumn(name = "company_todo_id", nullable = false)  // TO-DO FK
    private CompanyTodoEntity companyTodo;                   // 대상 TO-DO 항목

    @ManyToOne(fetch = FetchType.LAZY)                      // 다대일 – 여러 담당자 레코드가 한 회사 사용자에 연결
    @JoinColumn(name = "company_user_id", nullable = false)  // 회사 사용자 FK
    private CompanyUserEntity companyUser;                   // 담당자(CompanyUser)

    @Builder                                                 // 빌더 생성자
    public TodoAssigneeEntity(CompanyTodoEntity companyTodo,
                              CompanyUserEntity companyUser) {
        this.companyTodo = companyTodo;                      // TO-DO 설정
        this.companyUser = companyUser;                      // 담당자 설정
    }
}
