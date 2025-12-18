package com.adnstyle.esg_agent.core.api.domain.model;

import com.adnstyle.esg_agent.core.enums.CompanyUserRole;

import java.time.LocalDateTime;

/**
 * 회사별 사용자(담당자) 응답 DTO.
 */
public record CompanyUserResponse(
        Long id,                                                 // 회사 사용자 ID
        Long userAccountId,                                      // 전역 계정 ID
        Long companyId,                                          // 회사 ID
        String email,                                            // 이메일
        String name,                                             // 이름
        CompanyUserRole role,                                    // 회사 내 역할
        String title,                                            // 직책
        String department,                                       // 부서
        String phone,                                            // 연락처
        Boolean defaultApprover,                                 // 기본 결재자 여부
        LocalDateTime createdAt                                  // 생성일시
) {
}
