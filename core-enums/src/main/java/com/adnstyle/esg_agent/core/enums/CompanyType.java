package com.adnstyle.esg_agent.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 기업 형태(Enum).
 * - 개인기업, 법인기업 등.
 */
@Getter
@AllArgsConstructor
public enum CompanyType {

    // 개인사업자 형태의 기업
    INDIVIDUAL("개인기업", "INDIVIDUAL"),

    // 법인 형태의 기업
    CORPORATION("법인기업", "CORPORATION");

    // 화면 표시용 한글 이름
    private final String displayName;

    // 내부 로직/저장용 코드
    private final String code;
}
