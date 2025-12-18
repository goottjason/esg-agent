package com.adnstyle.esg_agent.core.db.repository;

import com.adnstyle.esg_agent.core.db.entity.ApprovalLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ApprovalLineEntity 레포지토리.
 */
@Repository
public interface ApprovalLineRepository
        extends JpaRepository<ApprovalLineEntity, Long> {

    /**
     * 특정 회사의 결재선 목록을 순서대로 조회.
     */
    List<ApprovalLineEntity> findAllByCompanyIdOrderByApprovalOrder(Long companyId);
}
