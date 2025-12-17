package com.adnstyle.esg_agent.core.db.entity;

import com.adnstyle.esg_agent.core.enums.ToDoCategory;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "todo_contents")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoContentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 500)
    private String link;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ToDoCategory category;

    @OneToMany(mappedBy = "todoContent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyTodoEntity> companyTodos = new ArrayList<>();

    @Builder
    public TodoContentEntity(String title, String link, String description, ToDoCategory category) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.category = category;
    }
}