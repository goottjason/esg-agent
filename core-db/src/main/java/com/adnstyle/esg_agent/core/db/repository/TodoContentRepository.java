package com.adnstyle.esg_agent.core.db.repository;

import com.adnstyle.esg_agent.core.db.entity.TodoContentEntity;
import com.adnstyle.esg_agent.core.enums.ToDoCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TodoContentEntity 레포지토리.
 */
@Repository
public interface TodoContentRepository
        extends JpaRepository<TodoContentEntity, Long> {

    /**
     * 카테고리로 TO-DO 마스터 목록 조회.
     */
    List<TodoContentEntity> findAllByCategory(ToDoCategory category);
}
