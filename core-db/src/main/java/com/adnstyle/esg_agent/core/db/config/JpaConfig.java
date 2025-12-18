package com.adnstyle.esg_agent.core.db.config;                   // 설정 패키지

import org.springframework.context.annotation.Configuration;     // @Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing; // JPA Auditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories; // 레포지토리 스캔

/**
 * JPA 설정 클래스.
 * - JPA Repository와 Auditing을 활성화한다.
 */
@Configuration                                                   // 스프링 설정 클래스
@EnableJpaRepositories(basePackages = "com.adnstyle.esg_agent.core.db.repository")
// JPA 레포지토리 패키지 스캔
@EnableJpaAuditing                                               // @CreatedDate, @LastModifiedDate 활성화
public class JpaConfig {
    // 별도 설정 필요 없으면 비워둬도 됨
}
