package com.adnstyle.esg_agent.core.api.controller;             // 컨트롤러 패키지

import com.adnstyle.esg_agent.core.api.domain.service.MasterDataService; // 서비스
import com.adnstyle.esg_agent.core.api.response.ApiResponse;    // 공통 응답
import com.adnstyle.esg_agent.core.db.entity.*;                 // 마스터 엔티티들
import lombok.RequiredArgsConstructor;                          // 생성자 주입
import org.springframework.http.ResponseEntity;                 // 응답 엔티티
import org.springframework.web.bind.annotation.*;               // REST 어노테이션

import java.util.List;                                          // List

/**
 * 직원수/매출/기관/산업 등 마스터 데이터 조회용 컨트롤러.
 */
@RestController
@RequestMapping("/api/v1/master-data")                          // prefix
@RequiredArgsConstructor
public class MasterDataController {

    private final MasterDataService masterDataService;           // 서비스 의존성

    /**
     * 직원 수 구간 전체 조회 API.
     */
    @GetMapping("/employee-sizes")                              // GET /master-data/employee-sizes
    public ResponseEntity<ApiResponse<List<EmployeeSizeRangeEntity>>> getEmployeeSizes() {

        List<EmployeeSizeRangeEntity> data = masterDataService.getEmployeeSizes();

        return ResponseEntity
                .ok(ApiResponse.success("직원 수 구간 조회 완료", data));
    }

    /**
     * 매출 구간 전체 조회 API.
     */
    @GetMapping("/revenue-ranges")                              // GET /master-data/revenue-ranges
    public ResponseEntity<ApiResponse<List<RevenueRangeEntity>>> getRevenueRanges() {

        List<RevenueRangeEntity> data = masterDataService.getRevenueRanges();

        return ResponseEntity
                .ok(ApiResponse.success("매출 구간 조회 완료", data));
    }

    /**
     * ESG 평가 기관 전체 조회 API.
     */
    @GetMapping("/evaluation-agencies")                         // GET /master-data/evaluation-agencies
    public ResponseEntity<ApiResponse<List<EvaluationAgencyEntity>>> getEvaluationAgencies() {

        List<EvaluationAgencyEntity> data = masterDataService.getEvaluationAgencies();

        return ResponseEntity
                .ok(ApiResponse.success("평가 기관 목록 조회 완료", data));
    }

    /**
     * 산업 분류 조회 API (전체 또는 특정 레벨).
     */
    @GetMapping("/industries")                                  // GET /master-data/industries
    public ResponseEntity<ApiResponse<List<IndustryClassificationEntity>>> getIndustries(
            @RequestParam(required = false) Integer level) {    // level 파라미터(옵션)

        List<IndustryClassificationEntity> data =
                masterDataService.getIndustries(level);

        return ResponseEntity
                .ok(ApiResponse.success("산업 분류 조회 완료", data));
    }
}
