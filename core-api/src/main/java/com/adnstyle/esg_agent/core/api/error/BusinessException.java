package com.adnstyle.esg_agent.core.api.error;

/**
 * 비즈니스 규칙 위반 시 발생하는 예외.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);                                          // 상위 생성자 호출
    }
}
