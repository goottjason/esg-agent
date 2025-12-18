package com.adnstyle.esg_agent.core.api.domain.model;

/**
 * 회사별 사용자 프로필 수정 요청 DTO.
 */
public record UpdateCompanyUserProfileRequest(
        String title,                                            // 직책
        String department,                                       // 부서
        String phone                                             // 연락처
) {
}
