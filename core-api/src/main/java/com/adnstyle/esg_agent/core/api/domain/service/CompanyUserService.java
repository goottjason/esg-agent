package com.adnstyle.esg_agent.core.api.domain.service;                    // 서비스 패키지

import com.adnstyle.esg_agent.core.api.domain.model.*;                    // CompanyUser 관련 DTO
import com.adnstyle.esg_agent.core.db.entity.CompanyEntity;               // 회사 엔티티
import com.adnstyle.esg_agent.core.db.entity.CompanyUserEntity;           // 회사 사용자 엔티티
import com.adnstyle.esg_agent.core.db.entity.UserAccountEntity;           // 전역 계정 엔티티
import com.adnstyle.esg_agent.core.db.repository.CompanyRepository;       // 회사 레포지토리
import com.adnstyle.esg_agent.core.db.repository.CompanyUserRepository;   // 회사 사용자 레포지토리
import com.adnstyle.esg_agent.core.db.repository.UserAccountRepository;   // 계정 레포지토리
import com.adnstyle.esg_agent.core.support.exception.BusinessException;   // 비즈니스 예외
import com.adnstyle.esg_agent.core.support.exception.ResourceNotFoundException; // 리소스 예외
import lombok.RequiredArgsConstructor;                                    // 생성자 주입
import org.springframework.stereotype.Service;                            // 서비스
import org.springframework.transaction.annotation.Transactional;          // 트랜잭션

import java.util.List;                                                    // 리스트
import java.util.stream.Collectors;                                       // 컬렉터

/**
 * 회사별 사용자/담당자(CompanyUser) 관련 비즈니스 로직 서비스.
 */
@Service                                                                    // 서비스 컴포넌트
@RequiredArgsConstructor                                                    // 생성자 주입
@Transactional                                                             // 기본 트랜잭션
public class CompanyUserService {

    private final CompanyUserRepository companyUserRepository;             // 회사 사용자 레포지토리
    private final CompanyRepository companyRepository;                     // 회사 레포지토리
    private final UserAccountRepository userAccountRepository;             // 전역 계정 레포지토리

    /**
     * 회사별 사용자 생성.
     */
    public CompanyUserResponse createCompanyUser(CreateCompanyUserRequest request) {
        // 회사 조회
        CompanyEntity company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new ResourceNotFoundException("회사를 찾을 수 없습니다."));

        // 전역 계정 조회
        UserAccountEntity userAccount = userAccountRepository.findById(request.userAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("사용자 계정을 찾을 수 없습니다."));

        // 이미 해당 회사에 매핑된 CompanyUser가 있는지 체크
        boolean exists = companyUserRepository
                .findByCompanyIdAndUserAccountId(request.companyId(), request.userAccountId())
                .isPresent();

        if (exists) {
            throw new BusinessException("이미 이 회사에 등록된 사용자입니다.");        // 중복 매핑 방지
        }

        // 엔티티 생성
        CompanyUserEntity entity = CompanyUserEntity.builder()
                .company(company)                                          // 회사 설정
                .userAccount(userAccount)                                  // 계정 설정
                .role(request.role())                                      // 역할 설정
                .title(request.title())                                    // 직책
                .department(request.department())                          // 부서
                .phone(request.phone())                                    // 연락처
                .defaultApprover(request.defaultApprover())                // 기본 결재자 여부
                .build();

        // 저장
        CompanyUserEntity saved = companyUserRepository.save(entity);

        // DTO 변환
        return mapToResponse(saved);
    }

    /**
     * 회사별 사용자 단건 조회.
     */
    @Transactional(readOnly = true)                                         // 읽기 전용
    public CompanyUserResponse getCompanyUser(Long companyUserId) {
        // ID로 조회
        CompanyUserEntity entity = companyUserRepository.findById(companyUserId)
                .orElseThrow(() -> new ResourceNotFoundException("회사 사용자를 찾을 수 없습니다."));

        // DTO 변환
        return mapToResponse(entity);
    }

    /**
     * 특정 회사에 속한 사용자 목록 조회.
     */
    @Transactional(readOnly = true)                                         // 읽기 전용
    public List<CompanyUserResponse> getCompanyUsers(Long companyId) {
        // 회사 존재 여부 확인
        companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("회사를 찾을 수 없습니다."));

        // 회사 사용자 목록 조회 후 DTO 변환
        return companyUserRepository.findAllByCompanyId(companyId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * 회사 사용자 프로필(직책/부서/연락처) 수정.
     */
    public CompanyUserResponse updateCompanyUserProfile(Long companyUserId,
                                                        UpdateCompanyUserProfileRequest request) {
        // 회사 사용자 조회
        CompanyUserEntity entity = companyUserRepository.findById(companyUserId)
                .orElseThrow(() -> new ResourceNotFoundException("회사 사용자를 찾을 수 없습니다."));

        // 프로필 정보 수정
        entity.updateProfile(
                request.title(),                                          // 직책
                request.department(),                                     // 부서
                request.phone()                                           // 연락처
        );

        // DTO 변환
        return mapToResponse(entity);
    }

    /**
     * 회사 사용자 역할 변경.
     */
    public CompanyUserResponse updateCompanyUserRole(Long companyUserId,
                                                     com.adnstyle.esg_agent.core.enums.CompanyUserRole role) {
        // 회사 사용자 조회
        CompanyUserEntity entity = companyUserRepository.findById(companyUserId)
                .orElseThrow(() -> new ResourceNotFoundException("회사 사용자를 찾을 수 없습니다."));

        // 역할 변경
        entity.updateRole(role);

        // DTO 변환
        return mapToResponse(entity);
    }

    /**
     * 기본 결재자 플래그 변경.
     */
    public CompanyUserResponse updateDefaultApprover(Long companyUserId,
                                                     boolean defaultApprover) {
        // 회사 사용자 조회
        CompanyUserEntity entity = companyUserRepository.findById(companyUserId)
                .orElseThrow(() -> new ResourceNotFoundException("회사 사용자를 찾을 수 없습니다."));

        // 기본 결재자 플래그 설정
        entity.updateDefaultApprover(defaultApprover);

        // DTO 변환
        return mapToResponse(entity);
    }

    /**
     * 회사 사용자 삭제.
     */
    public void deleteCompanyUser(Long companyUserId) {
        // 존재 여부 확인
        CompanyUserEntity entity = companyUserRepository.findById(companyUserId)
                .orElseThrow(() -> new ResourceNotFoundException("회사 사용자를 찾을 수 없습니다."));

        // 삭제
        companyUserRepository.delete(entity);
    }

    /**
     * 엔티티 → 응답 DTO 매핑 메서드.
     */
    private CompanyUserResponse mapToResponse(CompanyUserEntity entity) {
        return new CompanyUserResponse(
                entity.getId(),                                           // 회사 사용자 ID
                entity.getUserAccount().getId(),                          // 전역 계정 ID
                entity.getCompany().getId(),                              // 회사 ID
                entity.getUserAccount().getEmail(),                       // 이메일
                entity.getUserAccount().getName(),                        // 이름
                entity.getRole(),                                         // 회사 내 역할
                entity.getTitle(),                                        // 직책
                entity.getDepartment(),                                   // 부서
                entity.getPhone(),                                        // 연락처
                entity.isDefaultApprover(),                               // 기본 결재자 여부
                entity.getCreatedAt()                                     // 생성일시
        );
    }
}
