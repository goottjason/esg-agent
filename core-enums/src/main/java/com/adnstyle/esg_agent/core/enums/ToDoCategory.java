package com.adnstyle.esg_agent.core.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ToDoCategory {
    REQUIRED("필수", "REQUIRED"),
    NORMAL("보통", "NORMAL"),
    RECOMMENDED("추천", "RECOMMENDED");

    private final String displayName;
    private final String code;
}
