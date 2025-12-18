package com.adnstyle.esg_agent.core.db.repository;                // 레포지토리 패키지

import com.adnstyle.esg_agent.core.db.entity.CompanyEntity;       // Company 엔티티
import org.springframework.data.jpa.repository.JpaRepository;     // JPA 레포지토리
import org.springframework.data.jpa.repository.JpaSpecificationExecutor; // 동적 쿼리용
import org.springframework.stereotype.Repository;                 // 스프링 빈 표시

import java.util.Optional;                                       // Optional

/**
 * CompanyEntity에 대한 JPA Repository.
 */
@Repository
public interface CompanyRepository
        extends JpaRepository<CompanyEntity, Long>,               // 기본 CRUD
        JpaSpecificationExecutor<CompanyEntity> {         // 동적 검색을 위한 Specification 지원

    /**
     * 사업자등록번호로 회사 조회.
     */
    Optional<CompanyEntity> findByBizRegNum(String bizRegNum);    // where bizRegNum = ?

    /**
     * 사업자등록번호 존재 여부 확인.
     */
    boolean existsByBizRegNum(String bizRegNum);                  // exists 쿼리 생성
}
