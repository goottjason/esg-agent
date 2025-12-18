package com.adnstyle.esg_agent.core.api.domain.model;

import com.adnstyle.esg_agent.core.enums.CompanyUserRole;

import java.time.LocalDateTime;

/**
 * 회사별 사용자(담당자) 생성 요청 DTO.
 */
public record CreateCompanyUserRequest(
        Long userAccountId,                                      // 전역 계정 ID
        Long companyId,                                          // 회사 ID
        CompanyUserRole role,                                    // 회사 내 역할
        String title,                                            // 직책
        String department,                                       // 부서
        String phone,                                            // 연락처
        Boolean defaultApprover                                  // 기본 결재자 여부
) {
}
