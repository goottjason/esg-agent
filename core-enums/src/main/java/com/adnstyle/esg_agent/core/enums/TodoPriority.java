package com.adnstyle.esg_agent.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TO-DO 우선순위(Enum).
 */
@Getter
@AllArgsConstructor
public enum TodoPriority {

    // 가장 높은 우선순위
    HIGH("높음", 3),

    // 기본 우선순위
    NORMAL("보통", 2),

    // 낮은 우선순위
    LOW("낮음", 1);

    // 화면 표시용 이름
    private final String displayName;

    // 비교/정렬에 사용할 가중치
    private final int weight;
}
