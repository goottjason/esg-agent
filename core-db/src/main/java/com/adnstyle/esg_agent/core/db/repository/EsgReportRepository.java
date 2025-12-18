package com.adnstyle.esg_agent.core.db.repository;

import com.adnstyle.esg_agent.core.db.entity.EsgReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * EsgReportEntity 레포지토리.
 */
@Repository
public interface EsgReportRepository
        extends JpaRepository<EsgReportEntity, Long> {

    /**
     * 특정 회사의 ESG 리포트 목록 조회.
     */
    List<EsgReportEntity> findAllByCompanyId(Long companyId);
}
