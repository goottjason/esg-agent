package com.adnstyle.esg_agent.core.db.repository;

import com.adnstyle.esg_agent.core.db.entity.EmployeeSizeRangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * EmployeeSizeRangeEntity 레포지토리.
 */
@Repository
public interface EmployeeSizeRangeRepository
        extends JpaRepository<EmployeeSizeRangeEntity, Long> {

    /**
     * 코드로 직원 수 구간 조회.
     */
    Optional<EmployeeSizeRangeEntity> findByRangeCode(String rangeCode);
}
