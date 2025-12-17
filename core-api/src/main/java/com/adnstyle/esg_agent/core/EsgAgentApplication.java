package com.adnstyle.esg_agent.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(
        // DB 없이 컨트롤러만 테스트하려면, DataSource 자동설정을 잠시 끌 수도 있음
        exclude = {
                DataSourceAutoConfiguration.class,
                HibernateJpaAutoConfiguration.class
        }
)
public class EsgAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsgAgentApplication.class, args);
    }
}