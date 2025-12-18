package com.adnstyle.esg_agent.core.api.domain.model;

/**
 * 회사 리스트 조회용 간략 응답 DTO.
 */
public record CompanyListResponse(
        Long id,                                                 // 회사 ID
        String name,                                             // 회사명
        String bizRegNum,                                        // 사업자번호
        String ceoName                                           // 대표자명
) {
}
