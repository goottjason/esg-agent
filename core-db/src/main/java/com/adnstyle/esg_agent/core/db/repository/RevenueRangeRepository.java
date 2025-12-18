package com.adnstyle.esg_agent.core.db.repository;

import com.adnstyle.esg_agent.core.db.entity.RevenueRangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RevenueRangeEntity 레포지토리.
 */
@Repository
public interface RevenueRangeRepository
        extends JpaRepository<RevenueRangeEntity, Long> {

    /**
     * 코드로 매출 구간 조회.
     */
    Optional<RevenueRangeEntity> findByRangeCode(String rangeCode);
}
