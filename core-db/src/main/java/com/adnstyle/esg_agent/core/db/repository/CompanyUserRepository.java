package com.adnstyle.esg_agent.core.db.repository;                // 레포지토리 패키지

import com.adnstyle.esg_agent.core.db.entity.CompanyUserEntity;   // CompanyUser 엔티티
import org.springframework.data.jpa.repository.JpaRepository;     // JPA 레포지토리
import org.springframework.stereotype.Repository;                 // 스프링 빈

import java.util.List;                                            // 리스트
import java.util.Optional;                                       // Optional

/**
 * CompanyUserEntity에 대한 JPA Repository.
 * - 회사별 사용자/담당자 조회에 사용.
 */
@Repository
public interface CompanyUserRepository
        extends JpaRepository<CompanyUserEntity, Long> {          // <엔티티, PK 타입>

    /**
     * 특정 회사에 속한 모든 회사 사용자 목록 조회.
     *
     * @param companyId 회사 ID
     * @return CompanyUser 목록
     */
    List<CompanyUserEntity> findAllByCompanyId(Long companyId);   // where company.id = ?

    /**
     * 특정 UserAccount가 속한 회사 사용자 목록 조회.
     *
     * @param userAccountId 전역 계정 ID
     * @return CompanyUser 목록
     */
    List<CompanyUserEntity> findAllByUserAccountId(Long userAccountId); // where userAccount.id = ?

    /**
     * 특정 회사 + UserAccount 조합으로 회사 사용자 조회.
     *
     * @param companyId     회사 ID
     * @param userAccountId 전역 계정 ID
     * @return Optional<CompanyUserEntity>
     */
    Optional<CompanyUserEntity> findByCompanyIdAndUserAccountId(Long companyId,
                                                                Long userAccountId);
}
