package com.adnstyle.esg_agent.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회사 내부에서의 역할(Enum).
 * - 동일한 User가 여러 회사에 속할 수 있고,
 *   회사마다 다른 역할을 가질 수 있도록 분리.
 * - 예: MANAGER, APPROVER, VIEWER 등.
 */
@Getter
@AllArgsConstructor
public enum CompanyUserRole {

    // ESG 실무 담당자 (TO-DO 관리, ESG 데이터 입력 등)
    MANAGER("담당자", "MANAGER"),

    // 결재/승인 권한자 (팀장, 본부장, 대표 등)
    APPROVER("결재자", "APPROVER"),

    // 읽기 전용 사용자 (ESG 현황/리포트 조회만)
    VIEWER("조회자", "VIEWER");

    // 화면 표시용 한글 이름
    private final String displayName;

    // 내부 로직/저장용 코드
    private final String code;
}
