package com.adnstyle.esg_agent.core.db.repository;                // 레포지토리 패키지

import com.adnstyle.esg_agent.core.db.entity.UserAccountEntity;  // UserAccount 엔티티
import org.springframework.data.jpa.repository.JpaRepository;     // JPA 기본 레포지토리
import org.springframework.stereotype.Repository;                 // 스프링 빈 표시

import java.util.Optional;                                       // Optional 타입

/**
 * UserAccountEntity에 대한 JPA Repository.
 * - 로그인/계정 관련 조회에 사용.
 */
@Repository                                                       // 명시적으로 Repository 컴포넌트 표시
public interface UserAccountRepository
        extends JpaRepository<UserAccountEntity, Long> {          // <엔티티, PK 타입>

    /**
     * 이메일로 사용자 계정을 조회.
     *
     * @param email 이메일
     * @return Optional 래핑된 UserAccountEntity
     */
    Optional<UserAccountEntity> findByEmail(String email);        // 메서드 이름 기반 쿼리
}
