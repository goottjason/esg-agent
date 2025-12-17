package com.adnstyle.esg_agent.core.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_email", columnList = "email", unique = true),
        @Index(name = "idx_company_id", columnList = "company_id")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 50)
    private String department;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 11)
    private String phone;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApprovalLineEntity> approvalLines = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<TodoAssigneeEntity> assignedTodos = new ArrayList<>();

    @Builder
    public UserEntity(CompanyEntity company, String name, String title, String department,
                         String email, String phone, String password) {
        this.company = company;
        this.name = name;
        this.title = title;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public void updateProfile(String title, String department, String phone) {
        this.title = title;
        this.department = department;
        this.phone = phone;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}