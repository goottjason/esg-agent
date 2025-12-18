package com.adnstyle.esg_agent.core.api.domain.model;

import com.adnstyle.esg_agent.core.enums.UserRole;

/**
 * 플랫폼 계정 생성 요청 DTO.
 */
public record CreateUserAccountRequest(
        String email,                                            // 이메일(로그인 ID)
        String password,                                         // 비밀번호(평문)
        String name,                                             // 사용자 이름
        UserRole role                                            // 전역 역할
) {
}
