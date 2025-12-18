package com.adnstyle.esg_agent.core.api.domain.service;                    // 서비스 패키지

import com.adnstyle.esg_agent.core.api.domain.model.CreateUserAccountRequest; // 계정 생성 요청 DTO
import com.adnstyle.esg_agent.core.api.domain.model.UserAccountResponse;     // 계정 응답 DTO
import com.adnstyle.esg_agent.core.api.error.BusinessException;
import com.adnstyle.esg_agent.core.api.error.ResourceNotFoundException;
import com.adnstyle.esg_agent.core.db.entity.UserAccountEntity;             // 엔티티
import com.adnstyle.esg_agent.core.db.repository.UserAccountRepository;     // 레포지토리
import com.adnstyle.esg_agent.core.enums.UserRole;                          // 전역 롤 Enum
import lombok.RequiredArgsConstructor;                                      // 생성자 주입
import org.springframework.security.crypto.password.PasswordEncoder;        // 비밀번호 인코더
import org.springframework.stereotype.Service;                              // 서비스 빈
import org.springframework.transaction.annotation.Transactional;            // 트랜잭션

import java.util.List;                                                      // 리스트
import java.util.stream.Collectors;                                         // 스트림 컬렉터

/**
 * 플랫폼 전역 사용자 계정(UserAccount) 관련 비즈니스 로직을 담당하는 서비스.
 */
@Service                                                                     // 스프링 서비스 컴포넌트
@RequiredArgsConstructor                                                     // final 필드 생성자 자동 생성
@Transactional                                                              // 기본 트랜잭션 경계
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;              // UserAccount 레포지토리
    private final PasswordEncoder passwordEncoder;                          // 비밀번호 인코더

    /**
     * 새 사용자 계정 생성.
     *
     * @param request 계정 생성 요청 DTO
     * @return 생성된 계정 정보 응답 DTO
     */
    public UserAccountResponse createUserAccount(CreateUserAccountRequest request) {
        // 동일 이메일 계정이 이미 존재하는지 체크
        if (userAccountRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessException("이미 사용 중인 이메일입니다.");          // 중복 이메일 예외
        }

        // 비밀번호를 인코딩 (해시) 처리
        String encodedPassword = passwordEncoder.encode(request.password()); // 평문 → 해시

        // 엔티티 생성
        UserAccountEntity entity = UserAccountEntity.builder()
                .email(request.email())                                      // 이메일 설정
                .password(encodedPassword)                                   // 인코딩된 비밀번호 설정
                .name(request.name())                                        // 이름 설정
                .role(request.role() != null ? request.role() : UserRole.COMPANY_USER) // 기본 역할 설정
                .active(true)                                                // 기본 활성화
                .build();

        // DB에 저장
        UserAccountEntity saved = userAccountRepository.save(entity);

        // 엔티티 → 응답 DTO 변환
        return mapToResponse(saved);
    }

    /**
     * 계정 단건 조회.
     *
     * @param userAccountId 계정 ID
     * @return 응답 DTO
     */
    @Transactional(readOnly = true)                                         // 읽기 전용 트랜잭션
    public UserAccountResponse getUserAccount(Long userAccountId) {
        // ID로 엔티티 조회, 없으면 예외
        UserAccountEntity entity = userAccountRepository.findById(userAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자 계정을 찾을 수 없습니다."));

        // DTO로 변환
        return mapToResponse(entity);
    }

    /**
     * 모든 계정 목록 조회(관리자 기능용).
     */
    @Transactional(readOnly = true)                                         // 읽기 전용
    public List<UserAccountResponse> getAllUserAccounts() {
        // 전체 조회 후 스트림으로 DTO 변환
        return userAccountRepository.findAll()
                .stream()
                .map(this::mapToResponse)                                    // 각 엔티티를 DTO로 변환
                .collect(Collectors.toList());                               // 리스트로 수집
    }

    /**
     * 비밀번호 변경.
     *
     * @param userAccountId 계정 ID
     * @param currentPassword 현재 비밀번호(평문)
     * @param newPassword     새 비밀번호(평문)
     */
    public void changePassword(Long userAccountId,
                               String currentPassword,
                               String newPassword) {
        // 계정 조회
        UserAccountEntity entity = userAccountRepository.findById(userAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자 계정을 찾을 수 없습니다."));

        // 현재 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(currentPassword, entity.getPassword())) {
            throw new BusinessException("현재 비밀번호가 일치하지 않습니다.");     // 비밀번호 불일치
        }

        // 새 비밀번호 인코딩
        String encoded = passwordEncoder.encode(newPassword);

        // 엔티티에 반영
        entity.updatePassword(encoded);
    }

    /**
     * 계정 활성/비활성 상태 변경.
     */
    public void updateActive(Long userAccountId, boolean active) {
        // 계정 조회
        UserAccountEntity entity = userAccountRepository.findById(userAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자 계정을 찾을 수 없습니다."));

        // 활성 상태 변경
        entity.updateActive(active);
    }

    /**
     * 엔티티 → 응답 DTO 매핑.
     */
    private UserAccountResponse mapToResponse(UserAccountEntity entity) {
        return new UserAccountResponse(
                entity.getId(),                                              // 계정 ID
                entity.getEmail(),                                           // 이메일
                entity.getName(),                                            // 이름
                entity.getRole(),                                            // 전역 역할
                entity.getActive(),                                          // 활성 여부
                entity.getCreatedAt()                                        // 생성일시
        );
    }
}
