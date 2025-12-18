package com.adnstyle.esg_agent.core.api.error;                   // 에러 패키지

import com.adnstyle.esg_agent.core.api.response.ApiResponse;     // 공통 응답
import com.adnstyle.esg_agent.core.api.response.ErrorDetail;     // 에러 상세
import lombok.extern.slf4j.Slf4j;                                // 로깅
import org.springframework.http.HttpStatus;                      // HTTP 상태
import org.springframework.http.ResponseEntity;                  // 응답 엔티티
import org.springframework.web.bind.MethodArgumentNotValidException; // Bean Validation 예외
import org.springframework.web.bind.annotation.ControllerAdvice; // 전역 예외 처리
import org.springframework.web.bind.annotation.ExceptionHandler;  // 예외 핸들러
import org.springframework.web.context.request.WebRequest;        // 요청 정보

import java.time.LocalDateTime;                                  // 시간 타입
import java.util.stream.Collectors;                              // 컬렉터

/**
 * 전역 예외 처리기.
 * - 커스텀 예외 및 검증/알 수 없는 예외를 일관된 응답 포맷으로 변환.
 */
@Slf4j                                                           // lombok 로깅
@ControllerAdvice                                                // 모든 컨트롤러에 적용
public class GlobalExceptionHandler {

    /**
     * 리소스 미존재 예외 처리.
     */
    @ExceptionHandler(ResourceNotFoundException.class)           // 해당 타입 예외 처리
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(
            ResourceNotFoundException ex,
            WebRequest request) {

        log.warn("Resource not found: {}", ex.getMessage());     // 경고 로그

        ErrorDetail errorDetail = ErrorDetail.builder()
                .status(HttpStatus.NOT_FOUND.value())            // 404
                .code("RESOURCE_NOT_FOUND")                      // 에러 코드
                .message(ex.getMessage())                        // 예외 메시지
                .path(request.getDescription(false).substring(4))// URI 추출
                .timestamp(LocalDateTime.now())                  // 발생 시각
                .build();

        return new ResponseEntity<>(
                ApiResponse.error("리소스를 찾을 수 없습니다.", errorDetail),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * 비즈니스 예외 처리.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(
            BusinessException ex,
            WebRequest request) {

        log.warn("Business exception: {}", ex.getMessage());     // 경고 로그

        ErrorDetail errorDetail = ErrorDetail.builder()
                .status(HttpStatus.BAD_REQUEST.value())          // 400
                .code("BUSINESS_EXCEPTION")                      // 에러 코드
                .message(ex.getMessage())                        // 메시지
                .path(request.getDescription(false).substring(4))// URI
                .timestamp(LocalDateTime.now())                  // 시각
                .build();

        return new ResponseEntity<>(
                ApiResponse.error("요청을 처리할 수 없습니다.", errorDetail),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * 인증/인가 예외 처리.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<?>> handleUnauthorized(
            UnauthorizedException ex,
            WebRequest request) {

        log.warn("Unauthorized access: {}", ex.getMessage());    // 경고 로그

        ErrorDetail errorDetail = ErrorDetail.builder()
                .status(HttpStatus.UNAUTHORIZED.value())         // 401
                .code("UNAUTHORIZED")                            // 에러 코드
                .message(ex.getMessage())                        // 메시지
                .path(request.getDescription(false).substring(4))// URI
                .timestamp(LocalDateTime.now())                  // 시각
                .build();

        return new ResponseEntity<>(
                ApiResponse.error("인증이 필요합니다.", errorDetail),
                HttpStatus.UNAUTHORIZED
        );
    }

    /**
     * Bean Validation 검증 실패 처리.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        log.warn("Validation error: {}", ex.getMessage());       // 경고 로그

        // 필드 에러들을 "field: message" 형태로 합치기
        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorDetail errorDetail = ErrorDetail.builder()
                .status(HttpStatus.BAD_REQUEST.value())          // 400
                .code("VALIDATION_FAILED")                       // 에러 코드
                .message(errors)                                 // 상세 메시지
                .path(request.getDescription(false).substring(4))// URI
                .timestamp(LocalDateTime.now())                  // 시각
                .build();

        return new ResponseEntity<>(
                ApiResponse.error("입력값 검증 실패", errorDetail),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * 그 밖의 모든 예외 처리.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGlobalException(
            Exception ex,
            WebRequest request) {

        log.error("Unexpected error: {}", ex.getMessage(), ex);  // 에러 로그

        ErrorDetail errorDetail = ErrorDetail.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())// 500
                .code("INTERNAL_SERVER_ERROR")                   // 에러 코드
                .message("예상치 못한 오류가 발생했습니다.")        // 일반 메시지
                .path(request.getDescription(false).substring(4))// URI
                .timestamp(LocalDateTime.now())                  // 시각
                .build();

        return new ResponseEntity<>(
                ApiResponse.error("서버 오류가 발생했습니다.", errorDetail),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
