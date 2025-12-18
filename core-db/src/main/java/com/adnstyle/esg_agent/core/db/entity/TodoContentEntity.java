package com.adnstyle.esg_agent.core.db.entity;                  // 엔티티 패키지

import com.adnstyle.esg_agent.core.enums.ToDoCategory;          // TO-DO 카테고리 Enum
import jakarta.persistence.*;                                   // JPA 임포트
import lombok.AccessLevel;                                      // Lombok 접근 제어
import lombok.Builder;                                          // Lombok 빌더
import lombok.Getter;                                           // Lombok getter
import lombok.NoArgsConstructor;                                // Lombok 기본 생성자

import java.util.ArrayList;                                     // List 구현체
import java.util.List;                                          // List 인터페이스

/**
 * ESG TO-DO 마스터 항목 엔티티.
 * - 회사별 TO-DO(CompanyTodo)가 이 마스터를 참조한다.
 */
@Entity                                                         // JPA 엔티티
@Table(name = "todo_contents")                                  // 테이블 이름
@Getter                                                         // getter 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)              // 기본 생성자
public class TodoContentEntity extends BaseEntity {             // BaseEntity 상속

    @Id                                                         // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // auto increment
    private Long id;                                            // TO-DO 마스터 ID

    @Column(name = "title", nullable = false, length = 255)     // 제목 컬럼
    private String title;                                       // TO-DO 제목

    @Column(name = "link", length = 500)                        // 참고 링크 컬럼
    private String link;                                        // 관련 자료 URL

    @Column(name = "description",                               // 설명 컬럼
            nullable = false,
            columnDefinition = "TEXT")
    private String description;                                 // 세부 설명

    @Enumerated(EnumType.STRING)                                // Enum 문자열 저장
    @Column(name = "category", nullable = false, length = 50)   // 카테고리 컬럼
    private ToDoCategory category;                              // 카테고리(필수/보통/추천)

    @OneToMany(
            mappedBy = "todoContent",                           // CompanyTodoEntity.todoContent
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CompanyTodoEntity> companyTodos                // 이 마스터를 참조하는 회사별 TO-DO 목록
            = new ArrayList<>();

    @Builder                                                    // 빌더 생성자
    public TodoContentEntity(String title,
                             String link,
                             String description,
                             ToDoCategory category) {
        this.title = title;                                     // 제목 설정
        this.link = link;                                       // 링크 설정
        this.description = description;                         // 설명 설정
        this.category = category;                               // 카테고리 설정
    }

    /**
     * 내용 변경 메서드.
     */
    public void updateContent(String title,
                              String link,
                              String description,
                              ToDoCategory category) {
        this.title = title;                                     // 제목 수정
        this.link = link;                                       // 링크 수정
        this.description = description;                         // 설명 수정
        this.category = category;                               // 카테고리 수정
    }
}
