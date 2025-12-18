package com.adnstyle.esg_agent.core.api.domain.model;

import com.adnstyle.esg_agent.core.enums.UserRole;

import java.time.LocalDateTime;

/**
 * 플랫폼 계정 응답 DTO.
 */
public record UserAccountResponse(
        Long id,                                                 // 계정 ID
        String email,                                            // 이메일
        String name,                                             // 이름
        UserRole role,                                           // 전역 역할
        Boolean active,                                          // 활성 여부
        LocalDateTime createdAt                                  // 생성일시
) {
}
