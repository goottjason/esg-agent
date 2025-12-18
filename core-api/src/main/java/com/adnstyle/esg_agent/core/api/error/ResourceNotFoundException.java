package com.adnstyle.esg_agent.core.api.error;                   // 에러 패키지

/**
 * 리소스를 찾을 수 없을 때 발생하는 예외.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);                                          // 상위 생성자 호출
    }
}
