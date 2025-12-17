package com.adnstyle.esg_agent.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RevenueRange {
    UNDER_1B(0, 1_000_000_000L, "10억 미만", "UNDER_1B"),
    FROM_1B(1_000_000_000L, 2_000_000_000L, "10억 이상", "FROM_1B"),
    FROM_2B(2_000_000_000L, 5_000_000_000L, "20억 이상", "FROM_2B"),
    FROM_5B(5_000_000_000L, 10_000_000_000L, "50억 이상", "FROM_5B"),
    FROM_10B(10_000_000_000L, Long.MAX_VALUE, "100억 이상", "FROM_10B");

    private final long minRevenue;
    private final long maxRevenue;
    private final String displayName;
    private final String code;
}