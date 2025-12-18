package com.adnstyle.esg_agent.core.api.domain.model;

import com.adnstyle.esg_agent.core.enums.ToDoCategory;

import java.time.LocalDateTime;

/**
 * TO-DO 마스터 응답 DTO.
 */
public record TodoContentResponse(
        Long id,                                                 // 마스터 ID
        String title,                                            // 제목
        String link,                                             // 링크
        String description,                                      // 설명
        ToDoCategory category,                                   // 카테고리
        LocalDateTime createdAt                                  // 생성일시
) {
}
