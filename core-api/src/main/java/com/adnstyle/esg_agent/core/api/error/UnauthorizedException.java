package com.adnstyle.esg_agent.core.api.error;

/**
 * 인증/인가 관련 예외.
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);                                          // 상위 생성자 호출
    }
}
