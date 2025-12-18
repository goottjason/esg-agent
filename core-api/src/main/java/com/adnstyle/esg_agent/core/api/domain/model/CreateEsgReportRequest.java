package com.adnstyle.esg_agent.core.api.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ESG 리포트 생성 요청 DTO.
 */
public record CreateEsgReportRequest(
        Long companyId,                                          // 회사 ID
        Long evaluationAgencyId,                                 // 평가 기관 ID
        Long esgRatingId,                                        // 등급 ID(선택)
        LocalDate startDate,                                     // 시작일
        LocalDate endDate,                                       // 종료일
        String fileUrl                                           // 리포트 URL
) {
}
