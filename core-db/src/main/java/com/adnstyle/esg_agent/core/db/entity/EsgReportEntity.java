package com.adnstyle.esg_agent.core.db.entity;                  // 엔티티 패키지

import jakarta.persistence.*;                                   // JPA 임포트
import lombok.AccessLevel;                                      // Lombok 접근 제어
import lombok.Builder;                                          // Lombok 빌더
import lombok.Getter;                                           // Lombok getter
import lombok.NoArgsConstructor;                                // Lombok 기본 생성자

import java.time.LocalDate;                                     // 날짜 타입

/**
 * 회사별 ESG 평가 리포트 엔티티.
 * - 어떤 회사가 언제 어떤 기관으로부터 어떤 등급을 받았는지 기록.
 */
@Entity                                                         // JPA 엔티티
@Table(
        name = "esg_reports",                                   // 테이블 이름
        indexes = {
                @Index(name = "idx_esg_reports_company",        // 회사 기준 인덱스
                        columnList = "company_id"),
                @Index(name = "idx_esg_reports_agency",         // 기관 기준 인덱스
                        columnList = "evaluation_agency_id")
        }
)
@Getter                                                         // getter 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)              // 기본 생성자
public class EsgReportEntity extends BaseEntity {               // BaseEntity 상속

    @Id                                                         // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // auto increment
    private Long id;                                            // ESG 리포트 ID

    @ManyToOne(fetch = FetchType.LAZY)                          // 다대일 – 여러 리포트가 한 회사에 속함
    @JoinColumn(name = "company_id",                            // 회사 FK
            nullable = false)
    private CompanyEntity company;                              // 대상 회사

    @ManyToOne(fetch = FetchType.LAZY)                          // 다대일 – 여러 리포트가 한 기관의 평가
    @JoinColumn(name = "evaluation_agency_id",                  // 기관 FK
            nullable = false)
    private EvaluationAgencyEntity evaluationAgency;            // 평가 기관

    @ManyToOne(fetch = FetchType.LAZY)                          // 다대일 – 여러 리포트가 같은 등급일 수 있음
    @JoinColumn(name = "esg_rating_id")                         // 등급 FK (nullable 허용)
    private EsgRatingEntity esgRating;                          // ESG 등급

    @Column(name = "start_date", nullable = false)              // 평가 시작일
    private LocalDate startDate;                                // 시작일

    @Column(name = "end_date", nullable = false)                // 평가 종료일
    private LocalDate endDate;                                  // 종료일

    @Column(name = "file_url", length = 500)                    // 리포트 파일 URL
    private String fileUrl;                                     // 파일 저장 위치

    @Builder                                                    // 빌더 생성자
    public EsgReportEntity(CompanyEntity company,
                           EvaluationAgencyEntity evaluationAgency,
                           EsgRatingEntity esgRating,
                           LocalDate startDate,
                           LocalDate endDate,
                           String fileUrl) {
        this.company = company;                                 // 회사 설정
        this.evaluationAgency = evaluationAgency;               // 기관 설정
        this.esgRating = esgRating;                             // 등급 설정
        this.startDate = startDate;                             // 시작일 설정
        this.endDate = endDate;                                 // 종료일 설정
        this.fileUrl = fileUrl;                                 // 파일 URL 설정
    }

    /**
     * 현재 날짜 기준으로 보고서 유효기간이 지났는지 여부.
     *
     * @return true 이면 이미 만료된 보고서.
     */
    public boolean isExpired() {
        return LocalDate.now().isAfter(this.endDate);           // 오늘 > 종료일 인지 확인
    }

    /**
     * 보고서 정보 변경 메서드.
     */
    public void updateReport(EsgRatingEntity esgRating,
                             LocalDate endDate,
                             String fileUrl) {
        this.esgRating = esgRating;                             // 등급 수정
        this.endDate = endDate;                                 // 종료일 수정
        this.fileUrl = fileUrl;                                 // 파일 URL 수정
    }
}
