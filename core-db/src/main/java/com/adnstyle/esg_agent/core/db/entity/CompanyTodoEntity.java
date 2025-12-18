package com.adnstyle.esg_agent.core.db.entity;                  // 엔티티 패키지

import com.adnstyle.esg_agent.core.enums.TodoPriority;          // 우선순위 Enum
import com.adnstyle.esg_agent.core.enums.TodoStatus;            // 상태 Enum
import jakarta.persistence.*;                                   // JPA 임포트
import lombok.AccessLevel;                                      // Lombok 접근 제어
import lombok.Builder;                                          // Lombok 빌더
import lombok.Getter;                                           // Lombok getter
import lombok.NoArgsConstructor;                                // Lombok 기본 생성자

import java.time.LocalDate;                                     // 날짜 타입
import java.util.ArrayList;                                     // List 구현체
import java.util.List;                                          // List 인터페이스

/**
 * 회사별 ESG TO-DO 엔티티.
 * - 어떤 회사의 어떤 TO-DO 마스터를 언제까지 누가 담당하는지 표현.
 */
@Entity                                                         // JPA 엔티티
@Table(
        name = "company_todos",                                 // 테이블 이름
        indexes = {
                @Index(name = "idx_company_todos_company",      // 회사 기준 인덱스
                        columnList = "company_id"),
                @Index(name = "idx_company_todos_status",       // 상태 기준 인덱스
                        columnList = "status")
        }
)
@Getter                                                         // getter 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)              // 기본 생성자
public class CompanyTodoEntity extends BaseEntity {             // BaseEntity 상속

    @Id                                                         // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // auto increment
    private Long id;                                            // 회사별 TO-DO ID

    @ManyToOne(fetch = FetchType.LAZY)                          // 다대일 – 여러 TO-DO가 한 회사에 속함
    @JoinColumn(name = "company_id", nullable = false)          // 회사 ID FK
    private CompanyEntity company;                              // 대상 회사

    @ManyToOne(fetch = FetchType.LAZY)                          // 다대일 – 여러 TO-DO가 하나의 마스터를 참조
    @JoinColumn(name = "todo_content_id", nullable = false)     // TO-DO 마스터 FK
    private TodoContentEntity todoContent;                      // 마스터 TO-DO

    @Enumerated(EnumType.STRING)                                // Enum 문자열 저장
    @Column(name = "status", nullable = false, length = 50)     // 상태 컬럼
    private TodoStatus status = TodoStatus.PENDING;             // 기본 상태 = 미시작

    @Column(name = "is_completed", nullable = false)            // 완료 여부
    private Boolean isCompleted = false;                        // 기본값 false

    @Column(name = "due_date")                                  // 마감일 컬럼
    private LocalDate dueDate;                                  // 마감일

    @Enumerated(EnumType.STRING)                                // Enum 문자열 저장
    @Column(name = "priority", nullable = false, length = 50)   // 우선순위 컬럼
    private TodoPriority priority = TodoPriority.NORMAL;        // 기본 우선순위 보통

    @Column(name = "order_seq", nullable = false)               // 정렬/순서 컬럼
    private Integer orderSeq = 0;                               // 기본 0

    @OneToMany(
            mappedBy = "companyTodo",                           // TodoAssigneeEntity.companyTodo
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TodoAssigneeEntity> assignees                  // 담당자 목록
            = new ArrayList<>();

    @Builder                                                    // 빌더 생성자
    public CompanyTodoEntity(CompanyEntity company,
                             TodoContentEntity todoContent,
                             LocalDate dueDate,
                             TodoPriority priority,
                             Integer orderSeq) {
        this.company = company;                                 // 회사 설정
        this.todoContent = todoContent;                         // 마스터 TO-DO 설정
        this.dueDate = dueDate;                                 // 마감일 설정
        this.priority = priority != null                        // 우선순위 null 방지
                ? priority
                : TodoPriority.NORMAL;
        this.orderSeq = orderSeq != null                        // 순서 null 방지
                ? orderSeq
                : 0;
        this.status = TodoStatus.PENDING;                       // 초기 상태 설정
        this.isCompleted = false;                               // 초기 완료 여부 false
    }

    /**
     * TO-DO를 완료 상태로 변경.
     */
    public void markCompleted() {
        this.isCompleted = true;                                // 완료 플래그 true
        this.status = TodoStatus.COMPLETED;                     // 상태를 COMPLETED로 변경
    }

    /**
     * TO-DO를 다시 진행 전 상태로 변경.
     */
    public void markPending() {
        this.isCompleted = false;                               // 완료 플래그 false
        this.status = TodoStatus.PENDING;                       // 상태를 PENDING으로 변경
    }

    /**
     * 마감일, 우선순위, 순서 변경.
     */
    public void updateTodo(LocalDate dueDate,
                           TodoPriority priority,
                           Integer orderSeq) {
        this.dueDate = dueDate;                                 // 마감일 수정
        this.priority = priority;                               // 우선순위 수정
        this.orderSeq = orderSeq;                               // 순서 수정
    }
}
