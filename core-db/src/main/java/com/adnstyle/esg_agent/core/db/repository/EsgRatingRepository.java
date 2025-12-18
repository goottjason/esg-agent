package com.adnstyle.esg_agent.core.db.repository;

import com.adnstyle.esg_agent.core.db.entity.EsgRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * EsgRatingEntity 레포지토리.
 */
@Repository
public interface EsgRatingRepository
        extends JpaRepository<EsgRatingEntity, Long> {

    /**
     * 특정 평가 기관이 제공하는 등급 목록 조회.
     */
    List<EsgRatingEntity> findAllByEvaluationAgencyId(Long evaluationAgencyId);
}
