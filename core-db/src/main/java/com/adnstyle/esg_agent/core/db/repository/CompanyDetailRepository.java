package com.adnstyle.esg_agent.core.db.repository;

import com.adnstyle.esg_agent.core.db.entity.CompanyDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * CompanyDetailEntity에 대한 JPA Repository.
 */
@Repository
public interface CompanyDetailRepository
        extends JpaRepository<CompanyDetailEntity, Long> {

    /**
     * 회사 ID로 회사 상세 정보 조회.
     */
    Optional<CompanyDetailEntity> findByCompanyId(Long companyId);
}
