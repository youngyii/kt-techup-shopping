package com.kt.controller;

import com.kt.dto.UserCreateRequest;
import com.kt.dto.UserUpdatePasswordRequest;
import com.kt.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// 일반 사용자(User) 관련 API를 제공하는 Controller
// 기능: 회원가입, 로그인 ID 중복 체크, 비밀번호 변경
@Tag(name = "유저", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "유효성 검사 실패"),
        @ApiResponse(responseCode = "500", description = "서버 에러 - 백엔드에 바로 문의 바랍니다.")
        /* API 문서화 - Swagger vs RestDocs
         * Swagger: UI가 편리, 어노테이션 기반이라 작성 쉬움 / 단점: 코드 어노테이션 증가
         * RestDocs: 코드 침범 없음 / 단점: 테스트 기반이라 작성 더 어려움
         */
})
public class UserController {
    // UserService를 생성자 주입(DI)하여 사용
    private final UserService userService;

    /**
     * 회원가입
     * 예: POST /users (body: UserCreateRequest JSON)
     * 요청 JSON은 Jackson이 DTO로 매핑하며 비스니스 로직은 Service에서 처리
     */
    // FIXME: @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid UserCreateRequest request) {
        userService.create(request);
    }

    /**
     * 로그인 ID 중복 체크
     * 예: /users/duplicate-login-id?loginId=ktuser
     * @RequestParam 은 기본적으로 required = true
     */
    // FIXME: @GetMapping("/duplicate-login-id")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Boolean isDuplicateLoginId(@RequestParam String loginId) {
        return userService.isDuplicateLoginId(loginId);
    }

    /**
     * 비밀번호 변경
     * 예: PUT /users/{id}/update-password
     * id는 path로 전달되고 body에는 oldPassword와 newPassword 포함
     */
    @PutMapping("/{id}/update-password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(
            @PathVariable Integer id,
            @RequestBody @Valid UserUpdatePasswordRequest request
    ) {
        userService.changePassword(id, request.oldPassword(), request.newPassword());
        /* URI 설계 원칙: 리소스(유저)를 명확히 식별해야 함
        - /users → 모든 유저 (식별 불가)
        - /users/{id} → 특정 유저 (식별 가능)

        비밀번호 변경 API 설계 방식
        1) body(JSON)에 id 포함 → 비권장
        2) uri에 id 포함 (/users/{id}/update-password) → 명확
        3) 인증 객체(SecurityContext 등)에서 id 추출 → 권장
        */
    }
}