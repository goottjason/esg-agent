package com.adnstyle.esg_agent.core.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodoStatus {
    PENDING("미시작", "PENDING"),
    IN_PROGRESS("진행중", "IN_PROGRESS"),
    COMPLETED("완료", "COMPLETED");

    private final String displayName;
    private final String code;
}