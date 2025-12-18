package com.adnstyle.esg_agent.core.api.controller;             // 컨트롤러 패키지

import com.adnstyle.esg_agent.core.api.domain.model.*;          // TO-DO DTO들
import com.adnstyle.esg_agent.core.api.domain.service.TodoService; // 서비스
import com.adnstyle.esg_agent.core.api.response.ApiResponse;    // 공통 응답
import com.adnstyle.esg_agent.core.enums.TodoStatus;            // 상태 Enum
import lombok.RequiredArgsConstructor;                          // 생성자 주입
import org.springframework.http.HttpStatus;                     // HTTP 상태
import org.springframework.http.ResponseEntity;                 // 응답 엔티티
import org.springframework.web.bind.annotation.*;               // REST 어노테이션

import jakarta.validation.Valid;                                // Bean Validation
import java.util.List;                                          // List

/**
 * 회사별 TO-DO(CompanyTodo) 관련 REST API 컨트롤러.
 */
@RestController
@RequestMapping("/api/v1/todos")                                // prefix
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;                       // 서비스 의존성

    /**
     * TO-DO 생성 API.
     */
    @PostMapping                                                 // POST /todos
    public ResponseEntity<ApiResponse<CompanyTodoResponse>> createTodo(
            @Valid @RequestBody CreateCompanyTodoRequest request) {

        CompanyTodoResponse response = todoService.createCompanyTodo(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("TO-DO 생성 완료", response));
    }

    /**
     * 특정 회사의 TO-DO 목록 조회 API.
     */
    @GetMapping("/company/{companyId}")                          // GET /todos/company/{companyId}
    public ResponseEntity<ApiResponse<List<CompanyTodoResponse>>> getCompanyTodos(
            @PathVariable Long companyId,
            @RequestParam(required = false) TodoStatus status) { // 상태 필터(옵션)

        List<CompanyTodoResponse> response;

        if (status != null) {
            response = todoService.getCompanyTodosByStatus(companyId, status);
        } else {
            response = todoService.getCompanyTodos(companyId);
        }

        return ResponseEntity
                .ok(ApiResponse.success("TO-DO 목록 조회 완료", response));
    }

    /**
     * TO-DO 단건 조회 API.
     */
    @GetMapping("/{id}")                                         // GET /todos/{id}
    public ResponseEntity<ApiResponse<CompanyTodoResponse>> getTodo(
            @PathVariable Long id) {

        CompanyTodoResponse response = todoService.getTodo(id);

        return ResponseEntity
                .ok(ApiResponse.success("TO-DO 조회 완료", response));
    }

    /**
     * TO-DO 수정 API.
     */
    @PutMapping("/{id}")                                         // PUT /todos/{id}
    public ResponseEntity<ApiResponse<CompanyTodoResponse>> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCompanyTodoRequest request) {

        CompanyTodoResponse response = todoService.updateTodo(id, request);

        return ResponseEntity
                .ok(ApiResponse.success("TO-DO 수정 완료", response));
    }

    /**
     * TO-DO 삭제 API.
     */
    @DeleteMapping("/{id}")                                      // DELETE /todos/{id}
    public ResponseEntity<ApiResponse<Void>> deleteTodo(
            @PathVariable Long id) {

        todoService.deleteTodo(id);

        return ResponseEntity
                .ok(ApiResponse.success("TO-DO 삭제 완료", null));
    }

    /**
     * TO-DO 완료 처리 API.
     */
    @PutMapping("/{id}/complete")                                // PUT /todos/{id}/complete
    public ResponseEntity<ApiResponse<Void>> completeTodo(
            @PathVariable Long id) {

        todoService.completeTodo(id);

        return ResponseEntity
                .ok(ApiResponse.success("TO-DO 완료 처리", null));
    }

    /**
     * TO-DO 재활성화(미완료) 처리 API.
     */
    @PutMapping("/{id}/reactivate")                              // PUT /todos/{id}/reactivate
    public ResponseEntity<ApiResponse<Void>> reactivateTodo(
            @PathVariable Long id) {

        todoService.reactivateTodo(id);

        return ResponseEntity
                .ok(ApiResponse.success("TO-DO 재활성화 완료", null));
    }

    /**
     * TO-DO 담당자 재할당 API.
     */
    @PutMapping("/{id}/assign")                                  // PUT /todos/{id}/assign
    public ResponseEntity<ApiResponse<Void>> assignCompanyUsers(
            @PathVariable Long id,
            @RequestBody AssignTodoRequest request) {

        todoService.assignCompanyUsers(id, request.companyUserIds());

        return ResponseEntity
                .ok(ApiResponse.success("TO-DO 담당자 재할당 완료", null));
    }
}
