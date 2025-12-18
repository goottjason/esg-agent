package com.adnstyle.esg_agent.core.api.domain.model;

/**
 * 토큰 갱신 응답 DTO.
 */
public record RefreshTokenResponse(
        String accessToken                                       // 새 액세스 토큰
) {
}
