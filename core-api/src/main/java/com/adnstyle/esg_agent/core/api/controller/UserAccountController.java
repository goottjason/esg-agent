package com.adnstyle.esg_agent.core.api.controller;             // 컨트롤러 패키지

import com.adnstyle.esg_agent.core.api.domain.model.CreateUserAccountRequest; // DTO
import com.adnstyle.esg_agent.core.api.domain.model.UserAccountResponse;      // DTO
import com.adnstyle.esg_agent.core.api.domain.service.UserAccountService;     // 서비스
import com.adnstyle.esg_agent.core.api.response.ApiResponse;                  // 공통 응답
import lombok.RequiredArgsConstructor;                                        // 생성자 주입
import org.springframework.http.HttpStatus;                                   // HTTP 상태
import org.springframework.http.ResponseEntity;                               // 응답 엔티티
import org.springframework.web.bind.annotation.*;                             // REST 어노테이션

import jakarta.validation.Valid;                                              // Bean Validation
import java.util.List;                                                        // List

/**
 * 전역 사용자 계정(UserAccount) 관련 REST API 컨트롤러.
 */
@RestController                                                                // REST 컨트롤러
@RequestMapping("/api/v1/users")                                              // 기본 URL prefix
@RequiredArgsConstructor                                                      // 생성자 주입
public class UserAccountController {

    private final UserAccountService userAccountService;                      // 서비스 의존성

    /**
     * 사용자 계정 생성 API.
     */
    @PostMapping                                                               // POST /api/v1/users
    public ResponseEntity<ApiResponse<UserAccountResponse>> createUserAccount(
            @Valid @RequestBody CreateUserAccountRequest request) {           // 요청 바디 검증

        UserAccountResponse response = userAccountService.createUserAccount(request); // 서비스 호출

        return ResponseEntity
                .status(HttpStatus.CREATED)                                   // 201 Created
                .body(ApiResponse.success("사용자 계정 생성 완료", response)); // 성공 응답
    }

    /**
     * 사용자 계정 단건 조회 API.
     */
    @GetMapping("/{id}")                                                      // GET /api/v1/users/{id}
    public ResponseEntity<ApiResponse<UserAccountResponse>> getUserAccount(
            @PathVariable Long id) {                                          // 경로 변수

        UserAccountResponse response = userAccountService.getUserAccount(id); // 서비스 호출

        return ResponseEntity
                .ok(ApiResponse.success("사용자 계정 조회 완료", response));    // 200 OK
    }

    /**
     * 사용자 계정 전체 목록 조회 API.
     * - 관리자용.
     */
    @GetMapping                                                               // GET /api/v1/users
    public ResponseEntity<ApiResponse<List<UserAccountResponse>>> getAllUsers() {

        List<UserAccountResponse> response = userAccountService.getAllUserAccounts(); // 전체 조회

        return ResponseEntity
                .ok(ApiResponse.success("사용자 계정 목록 조회 완료", response));
    }
}
