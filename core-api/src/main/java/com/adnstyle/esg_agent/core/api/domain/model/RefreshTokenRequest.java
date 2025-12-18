package com.adnstyle.esg_agent.core.api.domain.model;

/**
 * 토큰 갱신 요청 DTO.
 */
public record RefreshTokenRequest(
        String refreshToken                                      // 리프레시 토큰
) {
}
