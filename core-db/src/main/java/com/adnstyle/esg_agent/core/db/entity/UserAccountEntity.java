package com.adnstyle.esg_agent.core.db.entity;

import com.adnstyle.esg_agent.core.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "user_accounts",
        indexes = {
                @Index(name = "idx_user_accounts_email", columnList = "email", unique = true)
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserAccountEntity extends BaseEntity {

    // ----- 컬럼
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password; // 암호화된 값

    @Column(nullable = false, length = 50)
    private String name; // 실명

    // NOTE: 닉네임 혹은 이니셜도 필요할까?

    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 50)
    private UserRole role;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    // ----- 연관관계
    @OneToMany(
            mappedBy = "userAccount", // CompanyUserEntity.userAccount 필드(user_acount_id 컬럼)에 의해 매핑
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CompanyUserEntity> companyUsers = new ArrayList<>();

    // ----- 도메인 메서드
    public void updateName(String name) { this.name = name; }

    public void updatePassword(String encodedPassword) { this.password = encodedPassword; }

    public void updateActive(Boolean active) { this.active = active; }

    public void updateRole(UserRole role) { this.role = role; }
}
