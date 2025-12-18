package com.adnstyle.esg_agent.core.db.repository;

import com.adnstyle.esg_agent.core.db.entity.IndustryClassificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * IndustryClassificationEntity 레포지토리.
 */
@Repository
public interface IndustryClassificationRepository
        extends JpaRepository<IndustryClassificationEntity, Long> {

    /**
     * 코드로 산업 분류 조회.
     */
    Optional<IndustryClassificationEntity> findByCode(String code);

    /**
     * 특정 레벨에 해당하는 산업 분류 목록 조회.
     */
    List<IndustryClassificationEntity> findAllByLevel(Integer level);

    /**
     * 특정 부모 ID를 가진 자식 산업 분류 목록 조회.
     */
    List<IndustryClassificationEntity> findAllByParentId(Long parentId);
}
