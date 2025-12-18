package com.adnstyle.esg_agent.core.api.domain.model;

import java.time.LocalDateTime;

/**
 * 회사 수정 요청 DTO.
 */
public record UpdateCompanyRequest(
        String ceoName,                                          // 대표자명
        String ceoPhone,                                         // 대표자 연락처
        String zipCode,                                          // 우편번호
        String address1,                                         // 주소1
        String address2                                          // 주소2
) {
}
