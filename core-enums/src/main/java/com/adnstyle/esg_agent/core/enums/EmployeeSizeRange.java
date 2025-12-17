package com.adnstyle.esg_agent.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmployeeSizeRange {
    MICRO(1, 25, "소기업 (1~25인)", "MICRO"),
    SMALL(26, 99, "소기업 (26~99인)", "SMALL"),
    MEDIUM(100, 299, "중기업 (100~299인)", "MEDIUM"),
    LARGE(300, 999, "중견기업 (300~999인)", "LARGE"),
    ENTERPRISE(1000, 999999, "대기업 (1000인 이상)", "ENTERPRISE");

    private final int minEmployees;
    private final int maxEmployees;
    private final String displayName;
    private final String code;
}
