package com.adnstyle.esg_agent.core.api.domain.model;

import java.util.List;

/**
 * TO-DO 담당자 재할당 요청 DTO.
 */
public record AssignTodoRequest(
        List<Long> companyUserIds                                // 새 담당자(CompanyUser) ID 목록
) {
}
