package com.adnstyle.esg_agent.core.api.domain.model;

import java.time.LocalDate;

/**
 * ESG 리포트 리스트 응답 DTO.
 */
public record EsgReportListResponse(
        Long id,                                                 // 리포트 ID
        String agencyName,                                       // 기관 이름
        String ratingName,                                       // 등급 이름
        LocalDate endDate,                                       // 종료일
        Boolean isExpired                                        // 만료 여부
) {
}
