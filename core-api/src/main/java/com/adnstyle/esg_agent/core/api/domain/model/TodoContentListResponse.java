package com.adnstyle.esg_agent.core.api.domain.model;

import com.adnstyle.esg_agent.core.enums.ToDoCategory;

/**
 * TO-DO 마스터 리스트 응답 DTO.
 */
public record TodoContentListResponse(
        Long id,                                                 // 마스터 ID
        String title,                                            // 제목
        ToDoCategory category                                    // 카테고리
) {
}
