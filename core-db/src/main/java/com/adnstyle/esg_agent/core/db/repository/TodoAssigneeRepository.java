package com.adnstyle.esg_agent.core.db.repository;

import com.adnstyle.esg_agent.core.db.entity.TodoAssigneeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TodoAssigneeEntity 레포지토리.
 */
@Repository
public interface TodoAssigneeRepository
        extends JpaRepository<TodoAssigneeEntity, Long> {

    /**
     * 특정 TO-DO에 할당된 담당자 목록 조회.
     */
    List<TodoAssigneeEntity> findAllByCompanyTodoId(Long companyTodoId);

    /**
     * 특정 TO-DO에 대한 담당자 매핑 전체 삭제.
     */
    void deleteByCompanyTodoId(Long companyTodoId);
}
