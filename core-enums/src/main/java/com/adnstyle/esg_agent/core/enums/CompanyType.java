package com.adnstyle.esg_agent.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CompanyType {
    INDIVIDUAL("개인기업", "INDIVIDUAL"),
    CORPORATION("법인기업", "CORPORATION");

    private final String displayName;
    private final String code;
}
