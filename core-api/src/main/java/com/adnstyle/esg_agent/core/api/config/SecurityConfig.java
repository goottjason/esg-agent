package com.adnstyle.esg_agent.core.api.config;                  // 보안 설정 패키지

import org.springframework.context.annotation.Bean;              // @Bean
import org.springframework.context.annotation.Configuration;     // @Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // WebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // BCrypt
import org.springframework.security.crypto.password.PasswordEncoder;    // PasswordEncoder
import org.springframework.security.web.SecurityFilterChain;     // 필터 체인

/**
 * Spring Security 최소 설정.
 * - PasswordEncoder Bean만 사용하고, 나머지는 기본 허용/비활성으로 구성 가능.
 */
@Configuration                                                   // 설정 클래스
@EnableWebSecurity                                               // 웹 보안 활성화
public class SecurityConfig {

    /**
     * PasswordEncoder Bean 정의.
     * - 서비스 계층에서 비밀번호 암호화에 사용된다.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();                      // 권장되는 BCrypt 구현체
    }

    /**
     * SecurityFilterChain 설정.
     * - 초기 개발 단계에서는 CSRF 등을 비활성화하고 모든 요청 허용.
     * - 나중에 JWT/세션 인증을 붙일 때 다시 강화하면 된다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())                    // CSRF 비활성화 (API 서버용)
                .cors(cors -> cors.disable())                    // CORS 별도 설정 없으면 비활성
                .headers(headers -> headers.disable())           // 기본 헤더 보안 비활성(필요 시 조정)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()                // 모든 요청 허용 (초기 개발용)
                );

        return http.build();                                     // 필터 체인 빌드
    }
}
