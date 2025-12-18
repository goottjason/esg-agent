package com.adnstyle.esg_agent.core.api.domain.model;

/**
 * 회사 상세(직원 수/매출/산업분류) 응답 DTO.
 */
public record CompanyDetailResponse(
        Long companyId,                                          // 회사 ID
        String companyName,                                      // 회사명
        String employeeSizeRange,                                // 직원 수 구간 이름
        String revenueRange,                                     // 매출 구간 이름
        String industryClassification                            // 산업 분류 이름
) {
}
