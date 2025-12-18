package com.adnstyle.esg_agent.core.api.domain.model;

import com.adnstyle.esg_agent.core.enums.TodoPriority;
import com.adnstyle.esg_agent.core.enums.TodoStatus;

import java.time.LocalDate;

/**
 * 회사별 TO-DO 수정 요청 DTO.
 */
public record UpdateCompanyTodoRequest(
        LocalDate dueDate,                                       // 마감일
        TodoPriority priority,                                   // 우선순위
        TodoStatus status,                                       // 상태
        Integer orderSeq                                         // 정렬 순서
) {
}
