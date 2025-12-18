package com.adnstyle.esg_agent.core.api.domain.service;                    // 서비스 패키지

import com.adnstyle.esg_agent.core.api.domain.model.*;                     // Company 관련 DTO 전체 임포트
import com.adnstyle.esg_agent.core.db.entity.*;                            // Company, Detail, Master 엔티티들
import com.adnstyle.esg_agent.core.db.repository.*;                        // 관련 레포지토리들
import com.adnstyle.esg_agent.core.support.exception.BusinessException;    // 비즈니스 예외
import com.adnstyle.esg_agent.core.support.exception.ResourceNotFoundException; // 리소스 예외
import lombok.RequiredArgsConstructor;                                     // 생성자 주입
import org.springframework.data.domain.Page;                               // 페이지 응답
import org.springframework.data.domain.PageRequest;                        // 페이지 요청
import org.springframework.data.domain.Pageable;                           // Pageable
import org.springframework.data.domain.Sort;                               // 정렬
import org.springframework.stereotype.Service;                             // 서비스 빈
import org.springframework.transaction.annotation.Transactional;           // 트랜잭션

import java.util.Optional;                                                 // Optional

/**
 * 회사(Company) 관련 비즈니스 로직 서비스.
 */
@Service                                                                    // 서비스 컴포넌트
@RequiredArgsConstructor                                                    // 생성자 주입
@Transactional                                                             // 기본 트랜잭션
public class CompanyService {

    private final CompanyRepository companyRepository;                      // 회사 레포지토리
    private final CompanyDetailRepository companyDetailRepository;          // 회사 상세 레포지토리
    private final EmployeeSizeRangeRepository employeeSizeRangeRepository;  // 직원 수 구간 레포지토리
    private final RevenueRangeRepository revenueRangeRepository;            // 매출 구간 레포지토리
    private final IndustryClassificationRepository industryClassificationRepository; // 산업 분류 레포지토리

    /**
     * 새 회사 생성.
     */
    public CompanyResponse createCompany(CreateCompanyRequest request) {
        // 사업자등록번호 중복 체크
        if (companyRepository.existsByBizRegNum(request.bizRegNum())) {
            throw new BusinessException("이미 등록된 사업자등록번호입니다.");        // 중복 예외
        }

        // 엔티티 생성
        CompanyEntity entity = CompanyEntity.builder()
                .name(request.name())                                       // 회사명
                .bizRegNum(request.bizRegNum())                             // 사업자번호
                .ceoName(request.ceoName())                                 // 대표자명
                .ceoPhone(request.ceoPhone())                               // 대표자 연락처
                .zipCode(request.zipCode())                                 // 우편번호
                .address1(request.address1())                               // 주소1
                .address2(request.address2())                               // 주소2
                .companyType(request.companyType())                         // 회사 유형
                .corpRegNum(request.corpRegNum())                           // 법인등록번호
                .build();

        // 저장
        CompanyEntity saved = companyRepository.save(entity);

        // DTO 변환 후 반환
        return mapToResponse(saved);
    }

    /**
     * 회사 단건 조회.
     */
    @Transactional(readOnly = true)                                         // 읽기 전용 트랜잭션
    public CompanyResponse getCompany(Long companyId) {
        // ID로 회사 조회
        CompanyEntity entity = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("회사를 찾을 수 없습니다."));

        // DTO 변환
        return mapToResponse(entity);
    }

    /**
     * 회사 목록 페이징 조회.
     */
    @Transactional(readOnly = true)                                         // 읽기 전용
    public Page<CompanyListResponse> searchCompanies(int page,
                                                     int size,
                                                     String sortBy) {
        // Pageable 생성 (기본: 내림차순)
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        // 회사 목록 페이징 조회 후, 각 엔티티를 리스트 DTO로 매핑
        return companyRepository.findAll(pageable)
                .map(this::mapToListResponse);
    }

    /**
     * 회사 정보 수정.
     */
    public CompanyResponse updateCompany(Long companyId,
                                         UpdateCompanyRequest request) {
        // 회사 조회
        CompanyEntity entity = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("회사를 찾을 수 없습니다."));

        // 기본 정보 수정
        entity.updateBasicInfo(
                request.ceoName(),                                           // 대표자명
                request.ceoPhone(),                                          // 연락처
                request.zipCode(),                                           // 우편번호
                request.address1(),                                          // 주소1
                request.address2()                                           // 주소2
        );

        // 변경된 엔티티를 DTO로 매핑하여 반환
        return mapToResponse(entity);
    }

    /**
     * 회사 삭제.
     * - Cascade 설정에 따라 CompanyDetail, CompanyUser, Todo, EsgReport 등이 함께 삭제된다.
     */
    public void deleteCompany(Long companyId) {
        // 회사 조회
        CompanyEntity entity = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("회사를 찾을 수 없습니다."));

        // 삭제
        companyRepository.delete(entity);
    }

    /**
     * 회사 상세 정보 설정/수정.
     *
     * @param companyId           회사 ID
     * @param employeeSizeId      직원 수 구간 ID
     * @param revenueRangeId      매출 구간 ID
     * @param industryId          산업 분류 ID
     */
    public CompanyDetailResponse setupCompanyDetails(Long companyId,
                                                     Long employeeSizeId,
                                                     Long revenueRangeId,
                                                     Long industryId) {
        // 회사 조회
        CompanyEntity company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("회사를 찾을 수 없습니다."));

        // 직원 수 구간 조회 (null 허용)
        EmployeeSizeRangeEntity employeeSize = Optional.ofNullable(employeeSizeId)
                .flatMap(employeeSizeRangeRepository::findById)
                .orElse(null);

        // 매출 구간 조회 (null 허용)
        RevenueRangeEntity revenueRange = Optional.ofNullable(revenueRangeId)
                .flatMap(revenueRangeRepository::findById)
                .orElse(null);

        // 산업 분류 조회 (null 허용)
        IndustryClassificationEntity industry = Optional.ofNullable(industryId)
                .flatMap(industryClassificationRepository::findById)
                .orElse(null);

        // 기존 상세 정보 조회
        CompanyDetailEntity detail = companyDetailRepository.findByCompanyId(companyId)
                .orElse(null);

        if (detail == null) {                                                // 없으면 새로 생성
            detail = CompanyDetailEntity.builder()
                    .company(company)                                        // 회사 설정
                    .employeeSizeRange(employeeSize)                        // 직원 수 구간 설정
                    .revenueRange(revenueRange)                             // 매출 구간 설정
                    .industryClassification(industry)                       // 산업 분류 설정
                    .build();

            detail = companyDetailRepository.save(detail);                  // 저장
            company.updateDetail(detail);                                      // 회사에 연결
        } else {                                                            // 있으면 수정
            detail.updateDetails(employeeSize, revenueRange, industry);     // 값 변경
        }

        // 응답 DTO 생성
        String employeeSizeName =                                      // 직원 수 구간 이름
                detail.getEmployeeSizeRange() != null
                        ? detail.getEmployeeSizeRange().getRangeName()
                        : null;

        String revenueRangeName =                                      // 매출 구간 이름
                detail.getRevenueRange() != null
                        ? detail.getRevenueRange().getRangeName()
                        : null;

        String industryName =                                          // 산업 분류 이름
                detail.getIndustryClassification() != null
                        ? detail.getIndustryClassification().getName()
                        : null;

        return new CompanyDetailResponse(
                company.getId(),                                       // 회사 ID
                company.getName(),                                     // 회사명
                employeeSizeName,                                      // 직원 수 구간 이름
                revenueRangeName,                                      // 매출 구간 이름
                industryName                                           // 산업 분류 이름
        );
    }

    /**
     * 엔티티 → 상세 응답 DTO 매핑.
     */
    private CompanyResponse mapToResponse(CompanyEntity entity) {
        return new CompanyResponse(
                entity.getId(),                                        // 회사 ID
                entity.getName(),                                      // 이름
                entity.getBizRegNum(),                                 // 사업자번호
                entity.getCeoName(),                                   // 대표자명
                entity.getCeoPhone(),                                  // 연락처
                entity.getZipCode(),                                   // 우편번호
                entity.getAddress1(),                                  // 주소1
                entity.getAddress2(),                                  // 주소2
                entity.getCompanyType(),                               // 회사 유형
                entity.getCorpRegNum(),                                // 법인등록번호
                entity.getCreatedAt(),                                 // 생성일시
                entity.getUpdatedAt()                                  // 수정일시
        );
    }

    /**
     * 엔티티 → 리스트 응답 DTO 매핑.
     */
    private CompanyListResponse mapToListResponse(CompanyEntity entity) {
        return new CompanyListResponse(
                entity.getId(),                                            // 회사 ID
                entity.getName(),                                          // 회사명
                entity.getBizRegNum(),                                     // 사업자번호
                entity.getCeoName()                                        // 대표자명
        );
    }
}
