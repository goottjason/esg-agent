package com.adnstyle.esg_agent.core.api.domain.model;

/**
 * 로그인 응답 DTO.
 * - 액세스 토큰 + 사용자/회사 정보 등을 포함할 수 있다.
 */
public record LoginResponse(
        String accessToken,                                      // 액세스 토큰
        String refreshToken,                                     // 리프레시 토큰
        UserAccountResponse userAccount                          // 전역 계정 정보
) {
}
