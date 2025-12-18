package com.adnstyle.esg_agent.core.api.domain.model;            // DTO 패키지

import com.adnstyle.esg_agent.core.enums.CompanyType;            // 회사 타입 Enum

import java.time.LocalDateTime;                                  // 시간 타입

/**
 * 회사 생성 요청 DTO.
 * - 클라이언트 → API 요청 바디.
 */
public record CreateCompanyRequest(
        String name,                                             // 회사명
        String bizRegNum,                                        // 사업자등록번호
        String ceoName,                                          // 대표자명
        String ceoPhone,                                         // 대표자 연락처
        String zipCode,                                          // 우편번호
        String address1,                                         // 주소1
        String address2,                                         // 주소2
        CompanyType companyType,                                 // 회사 유형
        String corpRegNum                                        // 법인등록번호(선택)
) {
}
