package com.adnstyle.esg_agent.core.api.domain.model;

/**
 * TO-DO 완료/미완료 토글 요청 DTO.
 */
public record ToggleTodoCompleteRequest(
        Boolean isCompleted                                      // 완료 여부
) {
}
