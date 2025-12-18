package com.adnstyle.esg_agent.core.api.controller;             // 컨트롤러 패키지

import com.adnstyle.esg_agent.core.api.domain.model.*;          // Company DTO들
import com.adnstyle.esg_agent.core.api.domain.service.CompanyService; // 서비스
import com.adnstyle.esg_agent.core.api.response.ApiResponse;    // 공통 응답
import lombok.RequiredArgsConstructor;                          // 생성자 주입
import org.springframework.data.domain.Page;                    // 페이지 타입
import org.springframework.http.HttpStatus;                     // HTTP 상태
import org.springframework.http.ResponseEntity;                 // 응답 엔티티
import org.springframework.web.bind.annotation.*;               // REST 어노테이션

import jakarta.validation.Valid;                                // Bean Validation

/**
 * 회사(Company) 관련 REST API 컨트롤러.
 */
@RestController
@RequestMapping("/api/v1/companies")                            // 회사 API prefix
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;                 // 서비스 의존성

    /**
     * 회사 생성 API.
     */
    @PostMapping                                                 // POST /companies
    public ResponseEntity<ApiResponse<CompanyResponse>> createCompany(
            @Valid @RequestBody CreateCompanyRequest request) {  // 요청 바디

        CompanyResponse response = companyService.createCompany(request); // 서비스 호출

        return ResponseEntity
                .status(HttpStatus.CREATED)                      // 201
                .body(ApiResponse.success("회사 생성 완료", response));
    }

    /**
     * 회사 단건 조회 API.
     */
    @GetMapping("/{id}")                                         // GET /companies/{id}
    public ResponseEntity<ApiResponse<CompanyResponse>> getCompany(
            @PathVariable Long id) {                             // 경로 변수

        CompanyResponse response = companyService.getCompany(id); // 서비스 호출

        return ResponseEntity
                .ok(ApiResponse.success("회사 조회 완료", response)); // 200
    }

    /**
     * 회사 목록 페이징 조회 API.
     */
    @GetMapping                                                  // GET /companies
    public ResponseEntity<ApiResponse<Page<CompanyListResponse>>> searchCompanies(
            @RequestParam(defaultValue = "0") int page,          // 페이지 번호
            @RequestParam(defaultValue = "10") int size,         // 페이지 크기
            @RequestParam(defaultValue = "id") String sortBy) {  // 정렬 기준 필드

        Page<CompanyListResponse> response =
                companyService.searchCompanies(page, size, sortBy); // 서비스 호출

        return ResponseEntity
                .ok(ApiResponse.success("회사 목록 조회 완료", response)); // 200
    }

    /**
     * 회사 정보 수정 API.
     */
    @PutMapping("/{id}")                                         // PUT /companies/{id}
    public ResponseEntity<ApiResponse<CompanyResponse>> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCompanyRequest request) {  // 수정 요청 DTO

        CompanyResponse response = companyService.updateCompany(id, request); // 서비스 호출

        return ResponseEntity
                .ok(ApiResponse.success("회사 정보 수정 완료", response));
    }

    /**
     * 회사 삭제 API.
     */
    @DeleteMapping("/{id}")                                      // DELETE /companies/{id}
    public ResponseEntity<ApiResponse<Void>> deleteCompany(
            @PathVariable Long id) {

        companyService.deleteCompany(id);                        // 삭제 수행

        return ResponseEntity
                .ok(ApiResponse.success("회사 삭제 완료", null));
    }

    /**
     * 회사 상세 정보(직원수/매출/산업분류) 설정 API.
     */
    @PostMapping("/{id}/details")                               // POST /companies/{id}/details
    public ResponseEntity<ApiResponse<CompanyDetailResponse>> setupCompanyDetails(
            @PathVariable Long id,                              // 회사 ID
            @RequestParam(required = false) Long employeeSizeId,// 직원 수 구간 ID
            @RequestParam(required = false) Long revenueRangeId,// 매출 구간 ID
            @RequestParam(required = false) Long industryId) {  // 산업 분류 ID

        CompanyDetailResponse response = companyService.setupCompanyDetails(
                id, employeeSizeId, revenueRangeId, industryId); // 서비스 호출

        return ResponseEntity
                .ok(ApiResponse.success("회사 상세 정보 설정 완료", response));
    }
}
