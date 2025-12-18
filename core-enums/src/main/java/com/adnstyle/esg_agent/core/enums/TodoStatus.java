package com.adnstyle.esg_agent.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TO-DO 상태(Enum).
 */
@Getter
@AllArgsConstructor
public enum TodoStatus {

    // 아직 시작하지 않음
    PENDING("미시작", "PENDING"),

    // 진행 중
    IN_PROGRESS("진행중", "IN_PROGRESS"),

    // 완료됨
    COMPLETED("완료", "COMPLETED");

    // 화면 표시용 이름
    private final String displayName;

    // 내부 로직/저장용 코드
    private final String code;
}
