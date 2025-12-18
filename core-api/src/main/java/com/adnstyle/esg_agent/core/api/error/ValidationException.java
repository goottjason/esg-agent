package com.adnstyle.esg_agent.core.api.error;

/**
 * 입력 값 검증 실패 예외 (필요 시 사용).
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);                                          // 상위 생성자 호출
    }
}
