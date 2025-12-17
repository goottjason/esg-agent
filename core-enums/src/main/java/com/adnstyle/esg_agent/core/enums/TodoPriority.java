package com.adnstyle.esg_agent.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodoPriority {
    HIGH("높음", 3),
    NORMAL("보통", 2),
    LOW("낮음", 1);

    private final String displayName;
    private final int weight;
}