package com.adnstyle.esg_agent.core.api.domain.service;                    // 서비스 패키지

import com.adnstyle.esg_agent.core.api.domain.model.*;                    // ESG DTO들
import com.adnstyle.esg_agent.core.db.entity.*;                            // ESG 엔티티들
import com.adnstyle.esg_agent.core.db.repository.*;                        // 레포지토리들
import com.adnstyle.esg_agent.core.support.exception.ResourceNotFoundException; // 리소스 예외
import lombok.RequiredArgsConstructor;                                     // 생성자 주입
import org.springframework.stereotype.Service;                             // 서비스
import org.springframework.transaction.annotation.Transactional;           // 트랜잭션

import java.util.List;                                                     // List
import java.util.stream.Collectors;                                        // Collectors

/**
 * 회사별 ESG 리포트(EsgReport) 비즈니스 로직 서비스.
 */
@Service                                                                    // 서비스 컴포넌트
@RequiredArgsConstructor                                                    // 생성자 주입
@Transactional                                                             // 기본 트랜잭션
public class EsgReportService {

    private final EsgReportRepository esgReportRepository;                 // ESG 리포트 레포지토리
    private final CompanyRepository companyRepository;                     // 회사 레포지토리
    private final EvaluationAgencyRepository evaluationAgencyRepository;   // 평가 기관 레포지토리
    private final EsgRatingRepository esgRatingRepository;                 // ESG 등급 레포지토리

    /**
     * ESG 리포트 생성.
     */
    public EsgReportResponse createEsgReport(CreateEsgReportRequest request) {
        // 회사 조회
        CompanyEntity company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new ResourceNotFoundException("회사를 찾을 수 없습니다."));

        // 평가 기관 조회
        EvaluationAgencyEntity agency = evaluationAgencyRepository.findById(request.evaluationAgencyId())
                .orElseThrow(() -> new ResourceNotFoundException("ESG 평가기관을 찾을 수 없습니다."));

        // 등급은 선택(Nullable)
        EsgRatingEntity rating = null;
        if (request.esgRatingId() != null) {
            rating = esgRatingRepository.findById(request.esgRatingId())
                    .orElseThrow(() -> new ResourceNotFoundException("ESG 등급을 찾을 수 없습니다."));
        }

        // ESG 리포트 엔티티 생성
        EsgReportEntity report = EsgReportEntity.builder()
                .company(company)                                          // 회사
                .evaluationAgency(agency)                                  // 평가 기관
                .esgRating(rating)                                         // 등급(옵션)
                .startDate(request.startDate())                            // 시작일
                .endDate(request.endDate())                                // 종료일
                .fileUrl(request.fileUrl())                                // 파일 URL
                .build();

        // 저장
        EsgReportEntity saved = esgReportRepository.save(report);

        // DTO로 변환 후 반환
        return mapToResponse(saved);
    }

    /**
     * 특정 회사의 ESG 리포트 목록 조회.
     */
    @Transactional(readOnly = true)                                         // 읽기 전용
    public List<EsgReportListResponse> getCompanyReports(Long companyId) {
        // 회사 존재 여부 확인
        companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("회사를 찾을 수 없습니다."));

        // 회사 ID 기준 리포트 조회 후 리스트 DTO로 매핑
        return esgReportRepository.findAllByCompanyId(companyId)
                .stream()
                .map(this::mapToListResponse)
                .collect(Collectors.toList());
    }

    /**
     * ESG 리포트 단건 조회.
     */
    @Transactional(readOnly = true)                                         // 읽기 전용
    public EsgReportResponse getReport(Long reportId) {
        // 리포트 조회
        EsgReportEntity report = esgReportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("ESG 리포트를 찾을 수 없습니다."));

        // DTO 변환
        return mapToResponse(report);
    }

    /**
     * ESG 리포트 수정.
     */
    public EsgReportResponse updateEsgReport(Long reportId,
                                             UpdateEsgReportRequest request) {
        // 리포트 조회
        EsgReportEntity report = esgReportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("ESG 리포트를 찾을 수 없습니다."));

        // 등급 조회(선택)
        EsgRatingEntity rating = null;
        if (request.esgRatingId() != null) {
            rating = esgRatingRepository.findById(request.esgRatingId())
                    .orElseThrow(() -> new ResourceNotFoundException("ESG 등급을 찾을 수 없습니다."));
        }

        // 엔티티 수정 메서드 호출
        report.updateReport(
                rating,                                                   // 새 등급
                request.endDate(),                                        // 새 종료일
                request.fileUrl()                                         // 새 파일 URL
        );

        // DTO 변환
        return mapToResponse(report);
    }

    /**
     * ESG 리포트 삭제.
     */
    public void deleteEsgReport(Long reportId) {
        // 리포트 조회
        EsgReportEntity report = esgReportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("ESG 리포트를 찾을 수 없습니다."));

        // 삭제
        esgReportRepository.delete(report);
    }

    /**
     * 엔티티 → 상세 응답 DTO 매핑.
     */
    private EsgReportResponse mapToResponse(EsgReportEntity report) {
        return new EsgReportResponse(
                report.getId(),                                           // 리포트 ID
                report.getCompany().getId(),                              // 회사 ID
                report.getEvaluationAgency().getAgencyName(),             // 기관 이름
                report.getEsgRating() != null                             // 등급 이름
                        ? report.getEsgRating().getRatingName()
                        : null,
                report.getEsgRating() != null                             // 등급 레벨
                        ? report.getEsgRating().getRatingLevel()
                        : null,
                report.getStartDate(),                                    // 시작일
                report.getEndDate(),                                      // 종료일
                report.isExpired(),                                       // 만료 여부
                report.getFileUrl(),                                      // 파일 URL
                report.getCreatedAt()                                     // 생성일시
        );
    }

    /**
     * 엔티티 → 리스트 응답 DTO 매핑.
     */
    private EsgReportListResponse mapToListResponse(EsgReportEntity report) {
        return new EsgReportListResponse(
                report.getId(),                                           // 리포트 ID
                report.getEvaluationAgency().getAgencyName(),             // 기관 이름
                report.getEsgRating() != null                             // 등급 이름
                        ? report.getEsgRating().getRatingName()
                        : "N/A",
                report.getEndDate(),                                      // 종료일
                report.isExpired()                                        // 만료 여부
        );
    }
}
