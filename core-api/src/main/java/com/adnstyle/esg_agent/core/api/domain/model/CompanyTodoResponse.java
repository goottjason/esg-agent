package com.adnstyle.esg_agent.core.api.domain.model;

import com.adnstyle.esg_agent.core.enums.ToDoCategory;
import com.adnstyle.esg_agent.core.enums.TodoPriority;
import com.adnstyle.esg_agent.core.enums.TodoStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 회사별 TO-DO 응답 DTO.
 */
public record CompanyTodoResponse(
        Long id,                                                 // TO-DO ID
        Long companyId,                                          // 회사 ID
        Long todoContentId,                                      // 마스터 ID
        String todoTitle,                                        // 제목
        String description,                                      // 설명
        ToDoCategory category,                                   // 카테고리
        TodoStatus status,                                       // 상태
        Boolean isCompleted,                                     // 완료 여부
        LocalDate dueDate,                                       // 마감일
        TodoPriority priority,                                   // 우선순위
        Integer orderSeq,                                        // 순서
        List<TodoAssigneeResponse> assignees,                    // 담당자 목록
        LocalDateTime createdAt                                  // 생성일시
) {
}
