package com.adnstyle.esg_agent.core.db.entity;

import com.adnstyle.esg_agent.core.enums.TodoPriority;
import com.adnstyle.esg_agent.core.enums.TodoStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company_todos", indexes = {
        @Index(name = "idx_company_id", columnList = "company_id"),
        @Index(name = "idx_status", columnList = "status")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyTodoEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_content_id", nullable = false)
    private TodoContentEntity todoContent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoStatus status = TodoStatus.PENDING;

    @Column(nullable = false)
    private Boolean isCompleted = false;

    @Column
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoPriority priority = TodoPriority.NORMAL;

    @Column(nullable = false)
    private Integer orderSeq = 0;

    @OneToMany(mappedBy = "companyTodo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodoAssigneeEntity> assignees = new ArrayList<>();

    @Builder
    public CompanyTodoEntity(CompanyEntity company, TodoContentEntity todoContent, LocalDate dueDate,
                             TodoPriority priority) {
        this.company = company;
        this.todoContent = todoContent;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public void markCompleted() {
        this.isCompleted = true;
        this.status = TodoStatus.COMPLETED;
    }

    public void markPending() {
        this.isCompleted = false;
        this.status = TodoStatus.PENDING;
    }

    public void updateTodo(LocalDate dueDate, TodoPriority priority, Integer orderSeq) {
        this.dueDate = dueDate;
        this.priority = priority;
        this.orderSeq = orderSeq;
    }
}