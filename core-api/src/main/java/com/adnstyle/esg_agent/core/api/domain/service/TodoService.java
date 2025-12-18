package com.adnstyle.esg_agent.core.api.domain.service;                    // 서비스 패키지

import com.adnstyle.esg_agent.core.api.domain.model.*;                    // TO-DO 관련 DTO들
import com.adnstyle.esg_agent.core.db.entity.*;                            // Company, CompanyUser, Todo 엔티티들
import com.adnstyle.esg_agent.core.db.repository.*;                        // 관련 레포지토리들
import com.adnstyle.esg_agent.core.enums.TodoPriority;                     // 우선순위 Enum
import com.adnstyle.esg_agent.core.enums.TodoStatus;                       // 상태 Enum
import com.adnstyle.esg_agent.core.support.exception.BusinessException;    // 비즈니스 예외
import com.adnstyle.esg_agent.core.support.exception.ResourceNotFoundException; // 리소스 예외
import lombok.RequiredArgsConstructor;                                     // 생성자 주입
import org.springframework.stereotype.Service;                             // 서비스 빈
import org.springframework.transaction.annotation.Transactional;           // 트랜잭션

import java.util.ArrayList;                                                // ArrayList
import java.util.List;                                                     // List 인터페이스
import java.util.stream.Collectors;                                        // 스트림 컬렉터

/**
 * 회사별 TO-DO(CompanyTodo) 및 담당자(TodoAssignee) 비즈니스 로직 서비스.
 */
@Service                                                                    // 서비스 컴포넌트
@RequiredArgsConstructor                                                    // 생성자 주입
@Transactional                                                             // 기본 트랜잭션 경계
public class TodoService {

    private final CompanyTodoRepository companyTodoRepository;             // 회사 TO-DO 레포지토리
    private final TodoContentRepository todoContentRepository;             // TO-DO 마스터 레포지토리
    private final TodoAssigneeRepository todoAssigneeRepository;           // 담당자 레포지토리
    private final CompanyRepository companyRepository;                     // 회사 레포지토리
    private final CompanyUserRepository companyUserRepository;             // 회사 사용자 레포지토리

    /**
     * 회사별 TO-DO 생성.
     */
    public CompanyTodoResponse createCompanyTodo(CreateCompanyTodoRequest request) {
        // 회사 조회
        CompanyEntity company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new ResourceNotFoundException("회사를 찾을 수 없습니다."));

        // TO-DO 마스터 조회
        TodoContentEntity todoContent = todoContentRepository.findById(request.todoContentId())
                .orElseThrow(() -> new ResourceNotFoundException("TO-DO 마스터를 찾을 수 없습니다."));

        // CompanyTodo 엔티티 생성 (상태/완료여부는 엔티티 기본값 활용)
        CompanyTodoEntity todo = CompanyTodoEntity.builder()
                .company(company)                                          // 회사 설정
                .todoContent(todoContent)                                  // 마스터 설정
                .dueDate(request.dueDate())                                // 마감일 설정
                .priority(request.priority() != null                       // 우선순위 null 방지
                        ? request.priority()
                        : TodoPriority.NORMAL)
                .orderSeq(0)                                               // 기본 순서 0
                .build();

        // 저장
        CompanyTodoEntity saved = companyTodoRepository.save(todo);

        // 담당자(CompanyUser) 연결
        if (request.assigneeCompanyUserIds() != null
                && !request.assigneeCompanyUserIds().isEmpty()) {
            assignCompanyUsers(saved.getId(), request.assigneeCompanyUserIds());
        }

        // DTO 변환 후 반환
        return mapToResponse(saved);
    }

    /**
     * 특정 회사의 TO-DO 목록 조회.
     */
    @Transactional(readOnly = true)                                         // 읽기 전용
    public List<CompanyTodoResponse> getCompanyTodos(Long companyId) {
        // 회사 존재 여부 확인
        companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("회사를 찾을 수 없습니다."));

        // 회사 ID 기준 TO-DO 목록을 순서대로 조회 후 DTO 변환
        return companyTodoRepository.findAllByCompanyIdOrderByOrderSeq(companyId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * 특정 회사의 상태별 TO-DO 목록 조회.
     */
    @Transactional(readOnly = true)                                         // 읽기 전용
    public List<CompanyTodoResponse> getCompanyTodosByStatus(Long companyId,
                                                             TodoStatus status) {
        // 회사 존재 여부 확인
        companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("회사를 찾을 수 없습니다."));

        // 상태 기반 조회
        return companyTodoRepository.findAllByCompanyIdAndStatus(companyId, status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * TO-DO 단건 조회.
     */
    @Transactional(readOnly = true)                                         // 읽기 전용
    public CompanyTodoResponse getTodo(Long todoId) {
        // TO-DO 조회
        CompanyTodoEntity todo = companyTodoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("TO-DO를 찾을 수 없습니다."));

        // DTO 변환
        return mapToResponse(todo);
    }

    /**
     * TO-DO 수정 (마감일, 우선순위, 상태, 순서).
     */
    public CompanyTodoResponse updateTodo(Long todoId,
                                          UpdateCompanyTodoRequest request) {
        // TO-DO 조회
        CompanyTodoEntity todo = companyTodoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("TO-DO를 찾을 수 없습니다."));

        // 상태 변경(옵션)
        if (request.status() != null) {
            todo.updateTodo(
                    request.dueDate() != null ? request.dueDate() : todo.getDueDate(),
                    request.priority() != null ? request.priority() : todo.getPriority(),
                    request.orderSeq() != null ? request.orderSeq() : todo.getOrderSeq()
            );
            // 상태는 별도로 설정
            todo.setStatus(request.status());                               // setter 필요 시 추가
        } else {
            // 상태가 없으면 일정/우선순위/순서만 변경
            todo.updateTodo(
                    request.dueDate(),
                    request.priority(),
                    request.orderSeq()
            );
        }

        // DTO 변환
        return mapToResponse(todo);
    }

    /**
     * TO-DO 삭제.
     */
    public void deleteTodo(Long todoId) {
        // TO-DO 조회
        CompanyTodoEntity todo = companyTodoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("TO-DO를 찾을 수 없습니다."));

        // 삭제
        companyTodoRepository.delete(todo);
    }

    /**
     * TO-DO를 완료 상태로 변경.
     */
    public void completeTodo(Long todoId) {
        // TO-DO 조회
        CompanyTodoEntity todo = companyTodoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("TO-DO를 찾을 수 없습니다."));

        // 완료 처리
        todo.markCompleted();
    }

    /**
     * TO-DO를 다시 미완료/대기 상태로 변경.
     */
    public void reactivateTodo(Long todoId) {
        // TO-DO 조회
        CompanyTodoEntity todo = companyTodoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("TO-DO를 찾을 수 없습니다."));

        // 미완료로 되돌림
        todo.markPending();
    }

    /**
     * TO-DO 담당자(CompanyUser) 재할당.
     */
    public void assignCompanyUsers(Long todoId,
                                   List<Long> companyUserIds) {
        // TO-DO 조회
        CompanyTodoEntity todo = companyTodoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("TO-DO를 찾을 수 없습니다."));

        // 기존 담당자 매핑 삭제
        todoAssigneeRepository.deleteByCompanyTodoId(todoId);

        // 새로 할당할 담당자 목록이 없으면 바로 종료
        if (companyUserIds == null || companyUserIds.isEmpty()) {
            return;
        }

        // 각 CompanyUserId에 대해 담당자 엔티티 생성 및 저장
        List<TodoAssigneeEntity> assignees = new ArrayList<>();
        for (Long companyUserId : companyUserIds) {
            CompanyUserEntity companyUser = companyUserRepository.findById(companyUserId)
                    .orElseThrow(() -> new ResourceNotFoundException("회사 사용자를 찾을 수 없습니다."));

            TodoAssigneeEntity assignee = TodoAssigneeEntity.builder()
                    .companyTodo(todo)                                    // TO-DO 설정
                    .companyUser(companyUser)                            // 담당자 설정
                    .build();

            assignees.add(assignee);
        }

        // 일괄 저장
        todoAssigneeRepository.saveAll(assignees);
    }

    /**
     * 엔티티 → 응답 DTO 매핑.
     */
    private CompanyTodoResponse mapToResponse(CompanyTodoEntity todo) {
        // 담당자 목록을 DTO로 변환
        List<TodoAssigneeResponse> assigneeResponses = todo.getAssignees()
                .stream()
                .map(a -> {
                    CompanyUserEntity cu = a.getCompanyUser();           // 회사 사용자 엔티티
                    UserAccountEntity ua = cu.getUserAccount();          // 전역 계정 엔티티
                    return new TodoAssigneeResponse(
                            cu.getId(),                                  // 회사 사용자 ID
                            ua.getId(),                                  // 전역 계정 ID
                            ua.getName(),                                // 이름
                            ua.getEmail(),                               // 이메일
                            cu.getTitle(),                               // 직책
                            cu.getDepartment()                           // 부서
                    );
                })
                .collect(Collectors.toList());

        // CompanyTodo 응답 DTO 생성
        return new CompanyTodoResponse(
                todo.getId(),                                            // TO-DO ID
                todo.getCompany().getId(),                               // 회사 ID
                todo.getTodoContent().getId(),                           // 마스터 ID
                todo.getTodoContent().getTitle(),                        // 제목
                todo.getTodoContent().getDescription(),                  // 설명
                todo.getTodoContent().getCategory(),                     // 카테고리
                todo.getStatus(),                                        // 상태
                todo.getIsCompleted(),                                   // 완료 여부
                todo.getDueDate(),                                       // 마감일
                todo.getPriority(),                                      // 우선순위
                todo.getOrderSeq(),                                      // 순서
                assigneeResponses,                                       // 담당자 목록
                todo.getCreatedAt()                                      // 생성일시
        );
    }
}
