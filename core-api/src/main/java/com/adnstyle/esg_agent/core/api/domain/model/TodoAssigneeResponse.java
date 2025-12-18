package com.adnstyle.esg_agent.core.api.domain.model;

/**
 * TO-DO 담당자 응답 DTO.
 */
public record TodoAssigneeResponse(
        Long companyUserId,                                      // 회사 사용자 ID
        Long userAccountId,                                      // 전역 계정 ID
        String name,                                             // 이름
        String email,                                            // 이메일
        String title,                                            // 직책
        String department                                        // 부서
) {
}
