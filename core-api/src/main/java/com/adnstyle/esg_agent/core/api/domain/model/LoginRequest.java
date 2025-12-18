package com.adnstyle.esg_agent.core.api.domain.model;

/**
 * 로그인 요청 DTO.
 */
public record LoginRequest(
        String email,                                            // 이메일
        String password                                          // 비밀번호
) {
}
