package com.adnstyle.esg_agent.core.api.domain.model;

import java.time.LocalDate;

/**
 * ESG 리포트 수정 요청 DTO.
 */
public record UpdateEsgReportRequest(
        Long esgRatingId,                                        // 등급 ID
        LocalDate endDate,                                       // 종료일
        String fileUrl                                           // 리포트 URL
) {
}
