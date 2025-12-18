package com.adnstyle.esg_agent.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TO-DO 카테고리(Enum).
 * - ESG 과제의 중요도/권장 여부를 분류.
 */
@Getter
@AllArgsConstructor
public enum ToDoCategory {

    // 반드시 해야 하는 필수 과제
    REQUIRED("필수", "REQUIRED"),

    // 일반적인 권장 과제
    NORMAL("보통", "NORMAL"),

    // 선택적으로 수행 가능한 과제
    RECOMMENDED("추천", "RECOMMENDED");

    // 화면 표시용 이름
    private final String displayName;

    // 내부 로직/저장용 코드
    private final String code;
}
