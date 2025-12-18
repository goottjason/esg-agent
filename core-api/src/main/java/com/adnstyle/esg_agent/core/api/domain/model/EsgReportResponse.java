package com.adnstyle.esg_agent.core.api.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ESG 리포트 응답 DTO.
 */
public record EsgReportResponse(
        Long id,                                                 // 리포트 ID
        Long companyId,                                          // 회사 ID
        String agencyName,                                       // 평가 기관 이름
        String ratingName,                                       // 등급 이름
        Integer ratingLevel,                                     // 등급 레벨
        LocalDate startDate,                                     // 시작일
        LocalDate endDate,                                       // 종료일
        Boolean isExpired,                                       // 만료 여부
        String fileUrl,                                          // 리포트 URL
        LocalDateTime createdAt                                  // 생성일시
) {
}
