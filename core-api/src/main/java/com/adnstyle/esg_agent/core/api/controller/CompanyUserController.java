package com.adnstyle.esg_agent.core.api.controller;             // 컨트롤러 패키지

import com.adnstyle.esg_agent.core.api.domain.model.*;          // CompanyUser DTO들
import com.adnstyle.esg_agent.core.api.domain.service.CompanyUserService; // 서비스
import com.adnstyle.esg_agent.core.api.response.ApiResponse;    // 공통 응답
import com.adnstyle.esg_agent.core.enums.CompanyUserRole;       // 회사 내 역할 Enum
import lombok.RequiredArgsConstructor;                          // 생성자 주입
import org.springframework.http.HttpStatus;                     // HTTP 상태
import org.springframework.http.ResponseEntity;                 // 응답 엔티티
import org.springframework.web.bind.annotation.*;               // REST 어노테이션

import jakarta.validation.Valid;                                // Bean Validation
import java.util.List;                                          // List

/**
 * 회사별 사용자/담당자(CompanyUser) 관련 REST API 컨트롤러.
 */
@RestController
@RequestMapping("/api/v1/company-users")                        // prefix
@RequiredArgsConstructor
public class CompanyUserController {

    private final CompanyUserService companyUserService;         // 서비스 의존성

    /**
     * 회사 사용자 생성 API.
     */
    @PostMapping                                                 // POST /company-users
    public ResponseEntity<ApiResponse<CompanyUserResponse>> createCompanyUser(
            @Valid @RequestBody CreateCompanyUserRequest request) {

        CompanyUserResponse response = companyUserService.createCompanyUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("회사 사용자 생성 완료", response));
    }

    /**
     * 회사 사용자 단건 조회 API.
     */
    @GetMapping("/{id}")                                         // GET /company-users/{id}
    public ResponseEntity<ApiResponse<CompanyUserResponse>> getCompanyUser(
            @PathVariable Long id) {

        CompanyUserResponse response = companyUserService.getCompanyUser(id);

        return ResponseEntity
                .ok(ApiResponse.success("회사 사용자 조회 완료", response));
    }

    /**
     * 특정 회사의 사용자 목록 조회 API.
     */
    @GetMapping("/company/{companyId}")                          // GET /company-users/company/{companyId}
    public ResponseEntity<ApiResponse<List<CompanyUserResponse>>> getCompanyUsers(
            @PathVariable Long companyId) {

        List<CompanyUserResponse> response = companyUserService.getCompanyUsers(companyId);

        return ResponseEntity
                .ok(ApiResponse.success("회사 사용자 목록 조회 완료", response));
    }

    /**
     * 회사 사용자 프로필 수정 API.
     */
    @PutMapping("/{id}/profile")                                 // PUT /company-users/{id}/profile
    public ResponseEntity<ApiResponse<CompanyUserResponse>> updateCompanyUserProfile(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCompanyUserProfileRequest request) {

        CompanyUserResponse response =
                companyUserService.updateCompanyUserProfile(id, request);

        return ResponseEntity
                .ok(ApiResponse.success("회사 사용자 프로필 수정 완료", response));
    }

    /**
     * 회사 사용자 역할 변경 API.
     */
    @PutMapping("/{id}/role")                                    // PUT /company-users/{id}/role
    public ResponseEntity<ApiResponse<CompanyUserResponse>> updateCompanyUserRole(
            @PathVariable Long id,
            @RequestParam CompanyUserRole role) {                 // 쿼리 파라미터로 역할 전달

        CompanyUserResponse response =
                companyUserService.updateCompanyUserRole(id, role);

        return ResponseEntity
                .ok(ApiResponse.success("회사 사용자 역할 변경 완료", response));
    }

    /**
     * 기본 결재자 플래그 변경 API.
     */
    @PutMapping("/{id}/default-approver")                        // PUT /company-users/{id}/default-approver
    public ResponseEntity<ApiResponse<CompanyUserResponse>> updateDefaultApprover(
            @PathVariable Long id,
            @RequestParam boolean defaultApprover) {              // 쿼리 파라미터로 bool 전달

        CompanyUserResponse response =
                companyUserService.updateDefaultApprover(id, defaultApprover);

        return ResponseEntity
                .ok(ApiResponse.success("기본 결재자 설정 변경 완료", response));
    }

    /**
     * 회사 사용자 삭제 API.
     */
    @DeleteMapping("/{id}")                                      // DELETE /company-users/{id}
    public ResponseEntity<ApiResponse<Void>> deleteCompanyUser(
            @PathVariable Long id) {

        companyUserService.deleteCompanyUser(id);

        return ResponseEntity
                .ok(ApiResponse.success("회사 사용자 삭제 완료", null));
    }
}
