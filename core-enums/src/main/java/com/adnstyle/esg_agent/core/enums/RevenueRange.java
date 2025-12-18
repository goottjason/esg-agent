package com.adnstyle.esg_agent.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 매출액 구간(Enum).
 * - 매출 규모에 따라 ESG 평가 기준을 다르게 적용할 때 사용.
 */
@Getter
@AllArgsConstructor
public enum RevenueRange {

    // 10억 미만
    UNDER_1B(0L, 1_000_000_000L, "10억 미만", "UNDER_1B"),

    // 10억 이상 ~ 20억 미만
    FROM_1B(1_000_000_000L, 2_000_000_000L, "10억 이상", "FROM_1B"),

    // 20억 이상 ~ 50억 미만
    FROM_2B(2_000_000_000L, 5_000_000_000L, "20억 이상", "FROM_2B"),

    // 50억 이상 ~ 100억 미만
    FROM_5B(5_000_000_000L, 10_000_000_000L, "50억 이상", "FROM_5B"),

    // 100억 이상
    FROM_10B(10_000_000_000L, Long.MAX_VALUE, "100억 이상", "FROM_10B");

    // 최소 매출액
    private final long minRevenue;

    // 최대 매출액
    private final long maxRevenue;

    // 화면 표시용 이름
    private final String displayName;

    // 내부 로직/저장용 코드
    private final String code;
}
