package com.adnstyle.esg_agent.core.api.response;                 // 공통 응답 패키지

import lombok.AllArgsConstructor;                                // 모든 필드 생성자
import lombok.Builder;                                           // 빌더
import lombok.Getter;                                            // getter

import java.time.LocalDateTime;                                  // 시간 타입

/**
 * 에러 상세 정보 DTO.
 */
@Getter                                                          // getter 생성
@Builder                                                         // 빌더
@AllArgsConstructor                                              // 모든 필드 생성자
public class ErrorDetail {

    private int status;                                          // HTTP 상태 코드
    private String code;                                         // 애플리케이션 에러 코드
    private String message;                                      // 에러 메시지
    private String path;                                         // 요청 경로
    private LocalDateTime timestamp;                             // 발생 시각
}
