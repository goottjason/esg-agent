package com.adnstyle.esg_agent.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 직원 수 구간(Enum).
 * - 직원 수에 따라 ESG 평가 강도/범위가 달라질 때 사용.
 */
@Getter
@AllArgsConstructor
public enum EmployeeSizeRange {

    // 소기업 1~25인
    MICRO(1, 25, "소기업 (1~25인)", "MICRO"),

    // 소기업 26~99인
    SMALL(26, 99, "소기업 (26~99인)", "SMALL"),

    // 중기업 100~299인
    MEDIUM(100, 299, "중기업 (100~299인)", "MEDIUM"),

    // 중견기업 300~999인
    LARGE(300, 999, "중견기업 (300~999인)", "LARGE"),

    // 대기업 1000인 이상
    ENTERPRISE(1000, 999_999, "대기업 (1000인 이상)", "ENTERPRISE");

    // 최소 직원 수
    private final int minEmployees;

    // 최대 직원 수
    private final int maxEmployees;

    // 화면 표시용 이름
    private final String displayName;

    // 내부 로직/저장용 코드
    private final String code;
}
