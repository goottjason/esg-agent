package com.adnstyle.esg_agent.core.db.repository;

import com.adnstyle.esg_agent.core.db.entity.EvaluationAgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * EvaluationAgencyEntity 레포지토리.
 */
@Repository
public interface EvaluationAgencyRepository
        extends JpaRepository<EvaluationAgencyEntity, Long> {

    /**
     * 코드로 평가 기관 조회.
     */
    Optional<EvaluationAgencyEntity> findByAgencyCode(String agencyCode);
}
