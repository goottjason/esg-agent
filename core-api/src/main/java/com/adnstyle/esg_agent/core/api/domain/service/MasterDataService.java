package com.adnstyle.esg_agent.core.api.domain.service;                    // 서비스 패키지

import com.adnstyle.esg_agent.core.db.entity.*;                            // 마스터 엔티티들
import com.adnstyle.esg_agent.core.db.repository.*;                        // 마스터 레포지토리들
import lombok.RequiredArgsConstructor;                                     // 생성자 주입
import org.springframework.stereotype.Service;                             // 서비스
import org.springframework.transaction.annotation.Transactional;           // 트랜잭션

import java.util.List;                                                     // List

/**
 * 화면에서 선택 목록으로 사용되는 마스터 데이터 조회용 서비스.
 * - 직원 수 구간, 매출 구간, 평가 기관, 산업 분류 등.
 */
@Service                                                                    // 서비스 컴포넌트
@RequiredArgsConstructor                                                    // 생성자 주입
@Transactional(readOnly = true)                                            // 전체 읽기 전용
public class MasterDataService {

    private final EmployeeSizeRangeRepository employeeSizeRangeRepository;  // 직원수
    private final RevenueRangeRepository revenueRangeRepository;            // 매출
    private final EvaluationAgencyRepository evaluationAgencyRepository;    // 기관
    private final IndustryClassificationRepository industryClassificationRepository; // 산업

    /**
     * 직원 수 구간 전체 조회.
     */
    public List<EmployeeSizeRangeEntity> getEmployeeSizes() {
        return employeeSizeRangeRepository.findAll();                       // 전체 조회
    }

    /**
     * 매출 구간 전체 조회.
     */
    public List<RevenueRangeEntity> getRevenueRanges() {
        return revenueRangeRepository.findAll();                            // 전체 조회
    }

    /**
     * ESG 평가 기관 전체 조회.
     */
    public List<EvaluationAgencyEntity> getEvaluationAgencies() {
        return evaluationAgencyRepository.findAll();                        // 전체 조회
    }

    /**
     * 산업 분류 전체 또는 특정 레벨 조회.
     *
     * @param level 조회할 레벨(null이면 전체)
     */
    public List<IndustryClassificationEntity> getIndustries(Integer level) {
        if (level != null) {                                                // 레벨이 지정되면
            return industryClassificationRepository.findAllByLevel(level); // 해당 레벨만 조회
        }
        return industryClassificationRepository.findAll();                  // 전체 조회
    }
}
