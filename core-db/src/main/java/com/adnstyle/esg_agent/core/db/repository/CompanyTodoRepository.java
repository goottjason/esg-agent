package com.adnstyle.esg_agent.core.db.repository;

import com.adnstyle.esg_agent.core.db.entity.CompanyTodoEntity;
import com.adnstyle.esg_agent.core.enums.TodoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CompanyTodoEntity 레포지토리.
 */
@Repository
public interface CompanyTodoRepository
        extends JpaRepository<CompanyTodoEntity, Long> {

    /**
     * 특정 회사의 TO-DO 목록을 순서대로 조회.
     */
    List<CompanyTodoEntity> findAllByCompanyIdOrderByOrderSeq(Long companyId);

    /**
     * 특정 회사 + 상태 기준 TO-DO 목록 조회.
     */
    List<CompanyTodoEntity> findAllByCompanyIdAndStatus(Long companyId,
                                                        TodoStatus status);

    /**
     * 특정 회사의 완료된 TO-DO 목록 조회.
     */
    List<CompanyTodoEntity> findAllByCompanyIdAndIsCompletedTrue(Long companyId);
}
