package com.adnstyle.esg_agent.core.api.domain.model;

import com.adnstyle.esg_agent.core.enums.ToDoCategory;
import com.adnstyle.esg_agent.core.enums.TodoPriority;
import com.adnstyle.esg_agent.core.enums.TodoStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 회사별 TO-DO 생성 요청 DTO.
 */
public record CreateCompanyTodoRequest(
        Long companyId,                                          // 회사 ID
        Long todoContentId,                                      // TO-DO 마스터 ID
        LocalDate dueDate,                                       // 마감일
        TodoPriority priority,                                   // 우선순위
        List<Long> assigneeCompanyUserIds                        // 담당자(CompanyUser) ID 목록
) {
}
