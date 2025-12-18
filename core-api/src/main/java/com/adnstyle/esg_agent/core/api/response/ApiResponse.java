package com.adnstyle.esg_agent.core.api.response;                 // 공통 응답 패키지

import com.fasterxml.jackson.annotation.JsonInclude;              // null 필드 제외
import lombok.AllArgsConstructor;                                // 모든 필드 생성자
import lombok.Builder;                                           // 빌더
import lombok.Getter;                                            // getter
import lombok.NoArgsConstructor;                                 // 기본 생성자

/**
 * 모든 API 응답의 공통 래퍼 클래스.
 * - 성공 여부, 메시지, 데이터, 에러 정보 포함.
 */
@Getter                                                          // 필드 getter 생성
@Builder                                                         // 빌더 패턴
@NoArgsConstructor                                               // 기본 생성자
@AllArgsConstructor                                              // 모든 필드 생성자
@JsonInclude(JsonInclude.Include.NON_NULL)                       // null 필드는 JSON에서 제외
public class ApiResponse<T> {

    private boolean success;                                     // 성공 여부
    private String message;                                      // 사용자 메시지
    private T data;                                              // 실제 데이터
    private ErrorDetail error;                                   // 에러 상세 정보

    /**
     * 성공 응답 생성 헬퍼 메서드.
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)                                   // 성공 플래그
                .message(message)                                // 메시지
                .data(data)                                      // 데이터
                .build();
    }

    /**
     * 실패 응답 생성 헬퍼 메서드.
     */
    public static <T> ApiResponse<T> error(String message, ErrorDetail error) {
        return ApiResponse.<T>builder()
                .success(false)                                  // 실패 플래그
                .message(message)                                // 메시지
                .error(error)                                    // 에러 상세
                .build();
    }
}
