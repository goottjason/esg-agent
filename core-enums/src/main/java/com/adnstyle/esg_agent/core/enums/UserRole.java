package com.adnstyle.esg_agent.core.enums; // 프로젝트 패키지에 맞게 조정

import lombok.AllArgsConstructor;          // 모든 필드를 인자로 받는 생성자 자동 생성
import lombok.Getter;                      // getter 메서드 자동 생성

/**
 * 플랫폼 전역 사용자 역할(Enum).
 * - 시스템 전체에서 공통으로 사용하는 Role 개념.
 * - 예: PLATFORM_ADMIN, COMPANY_ADMIN, COMPANY_USER 등.
 */
@Getter
@AllArgsConstructor
public enum UserRole {

    // 플랫폼 전체를 관리하는 최상위 관리자 (예: SaaS 운영사 관리자)
    PLATFORM_ADMIN("플랫폼 관리자", "ROLE_PLATFORM_ADMIN"),

    // 특정 회사의 최고 관리자 (회사 단위 설정/권한 관리)
    COMPANY_ADMIN("회사 관리자", "ROLE_COMPANY_ADMIN"),

    // 일반 회사 사용자 (담당자, 임직원 등)
    COMPANY_USER("회사 사용자", "ROLE_COMPANY_USER"),

    // ESG 평가를 보는 외부 감사/평가자 등의 계정이 필요할 때 확장용
    EXTERNAL_AUDITOR("외부 감사인", "ROLE_EXTERNAL_AUDITOR");

    // 화면 또는 로깅 시 사용할 한글 표시 이름
    private final String displayName;

    // Spring Security에서 사용할 권한 코드 (ROLE_ 접두어 포함)
    private final String authority;
}
