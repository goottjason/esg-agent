package com.adnstyle.esg_agent.core.db.entity;                 // 공통 엔티티 패키지

import jakarta.persistence.Column;                            // JPA 컬럼 매핑
import jakarta.persistence.EntityListeners;                   // 엔티티 리스너 설정
import jakarta.persistence.MappedSuperclass;                  // 상속용 슈퍼 클래스 설정
import jakarta.persistence.Version;                           // 낙관적 락 버전 필드
import lombok.AccessLevel;                                    // Lombok 접근 제어 레벨
import lombok.Getter;                                         // Lombok getter 생성
import lombok.NoArgsConstructor;                              // Lombok 기본 생성자 생성
import org.springframework.data.annotation.CreatedDate;       // 생성일 자동 기록
import org.springframework.data.annotation.LastModifiedDate;  // 수정일 자동 기록
import org.springframework.data.jpa.domain.support.AuditingEntityListener; // 감사(Auditing) 리스너

import java.time.LocalDateTime;                               // 날짜/시간 타입

/**
 * 모든 엔티티에서 공통으로 사용하는 기본 엔티티.
 * - 생성 시각, 수정 시각, 낙관적 락 버전 필드를 포함.
 * - @MappedSuperclass 이므로 실제 테이블은 생성되지 않고, 상속받은 엔티티에 컬럼이 포함됨.
 */
@Getter                                                       // 모든 필드에 대한 getter 메서드 생성
@MappedSuperclass                                             // 상속용 JPA 슈퍼 클래스
@EntityListeners(AuditingEntityListener.class)                // 생성/수정 시간 자동 관리
/*
    JPA는 지연로딩(Lazy Loading)을 위해 엔티티를 상속받는 프록시 객체를 생성.
    이 프록시가 부모 클래스(UserAccountEntity)의 기본 생성자를 super()로 호출해야 하므로, protected 접근자가 필요함.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)            // 보호 수준의 기본 생성자 생성
public abstract class BaseEntity {

    @CreatedDate                                              // 엔티티 최초 생성 시 자동으로 값 세팅
    @Column(nullable = false, updatable = false)              // null 불가, 수정 불가
    private LocalDateTime createdAt;                          // 생성 시각

    @LastModifiedDate                                         // 엔티티 수정 시 자동으로 값 세팅
    @Column(nullable = false)                                 // null 불가
    private LocalDateTime updatedAt;                          // 최종 수정 시각

    @Version                                                  // JPA 낙관적 락 버전 필드
    private Long version;                                     // 버전 값 (동시 수정 충돌 방지)
}
