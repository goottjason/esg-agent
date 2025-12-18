package com.adnstyle.esg_agent.core.api.controller;             // 컨트롤러 패키지

import com.adnstyle.esg_agent.core.api.domain.model.*;          // ESG DTO들
import com.adnstyle.esg_agent.core.api.domain.service.EsgReportService; // 서비스
import com.adnstyle.esg_agent.core.api.response.ApiResponse;    // 공통 응답
import lombok.RequiredArgsConstructor;                          // 생성자 주입
import org.springframework.http.HttpStatus;                     // HTTP 상태
import org.springframework.http.ResponseEntity;                 // 응답 엔티티
import org.springframework.web.bind.annotation.*;               // REST 어노테이션

import jakarta.validation.Valid;                                // Bean Validation
import java.util.List;                                          // List

/**
 * ESG 리포트(EsgReport) 관련 REST API 컨트롤러.
 */
@RestController
@RequestMapping("/api/v1/esg-reports")                          // prefix
@RequiredArgsConstructor
public class EsgReportController {

    private final EsgReportService esgReportService;             // 서비스 의존성

    /**
     * ESG 리포트 생성 API.
     */
    @PostMapping                                                 // POST /esg-reports
    public ResponseEntity<ApiResponse<EsgReportResponse>> createReport(
            @Valid @RequestBody CreateEsgReportRequest request) {

        EsgReportResponse response = esgReportService.createEsgReport(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("ESG 리포트 생성 완료", response));
    }

    /**
     * 특정 회사의 ESG 리포트 목록 조회 API.
     */
    @GetMapping("/company/{companyId}")                          // GET /esg-reports/company/{companyId}
    public ResponseEntity<ApiResponse<List<EsgReportListResponse>>> getCompanyReports(
            @PathVariable Long companyId) {

        List<EsgReportListResponse> response =
                esgReportService.getCompanyReports(companyId);

        return ResponseEntity
                .ok(ApiResponse.success("ESG 리포트 목록 조회 완료", response));
    }

    /**
     * ESG 리포트 단건 조회 API.
     */
    @GetMapping("/{id}")                                         // GET /esg-reports/{id}
    public ResponseEntity<ApiResponse<EsgReportResponse>> getReport(
            @PathVariable Long id) {

        EsgReportResponse response = esgReportService.getReport(id);

        return ResponseEntity
                .ok(ApiResponse.success("ESG 리포트 조회 완료", response));
    }

    /**
     * ESG 리포트 수정 API.
     */
    @PutMapping("/{id}")                                         // PUT /esg-reports/{id}
    public ResponseEntity<ApiResponse<EsgReportResponse>> updateReport(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEsgReportRequest request) {

        EsgReportResponse response = esgReportService.updateEsgReport(id, request);

        return ResponseEntity
                .ok(ApiResponse.success("ESG 리포트 수정 완료", response));
    }

    /**
     * ESG 리포트 삭제 API.
     */
    @DeleteMapping("/{id}")                                      // DELETE /esg-reports/{id}
    public ResponseEntity<ApiResponse<Void>> deleteReport(
            @PathVariable Long id) {

        esgReportService.deleteEsgReport(id);

        return ResponseEntity
                .ok(ApiResponse.success("ESG 리포트 삭제 완료", null));
    }
}
