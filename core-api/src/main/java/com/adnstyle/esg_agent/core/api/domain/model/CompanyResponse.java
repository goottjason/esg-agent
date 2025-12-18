package com.adnstyle.esg_agent.core.api.domain.model;

import com.adnstyle.esg_agent.core.enums.CompanyType;

import java.time.LocalDateTime;

/**
 * 회사 상세 정보 응답 DTO.
 */
public record CompanyResponse(
        Long id,                                                 // 회사 ID
        String name,                                             // 회사명
        String bizRegNum,                                        // 사업자번호
        String ceoName,                                          // 대표자명
        String ceoPhone,                                         // 대표자 연락처
        String zipCode,                                          // 우편번호
        String address1,                                         // 주소1
        String address2,                                         // 주소2
        CompanyType companyType,                                 // 회사 유형
        String corpRegNum,                                       // 법인등록번호
        LocalDateTime createdAt,                                 // 생성일시
        LocalDateTime updatedAt                                  // 수정일시
) {
}
