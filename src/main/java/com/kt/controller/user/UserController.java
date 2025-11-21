package com.kt.controller.user;

import com.kt.common.ApiResult;
import com.kt.common.SwaggerAssistance;
import com.kt.dto.user.UserRequest;
import com.kt.dto.user.UserUpdatePasswordRequest;
import com.kt.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// 일반 사용자(User) 관련 API를 제공하는 Controller
// 기능: 회원가입, 로그인 ID 중복 체크, 비밀번호 변경, 유저 삭제
@Tag(name = "User", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController extends SwaggerAssistance {
    // UserService를 생성자 주입(DI)하여 사용
    private final UserService userService;

    /**
     * 회원가입
     * 예: POST /users (body: UserCreateRequest JSON)
     * 요청 JSON은 Jackson이 DTO로 매핑하며 비스니스 로직은 Service에서 처리
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<Void> create(@RequestBody @Valid UserRequest.Create request) {
        userService.create(request);
        return ApiResult.ok();
    }

    /**
     * 로그인 ID 중복 체크
     * 예: /users/duplicate-login-id?loginId=ktuser
     *
     * @RequestParam 은 기본적으로 required = true
     */
    @GetMapping("/duplicate-login-id")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Boolean> isDuplicateLoginId(@RequestParam String loginId) {
        var result = userService.isDuplicateLoginId(loginId);

        return ApiResult.ok(result);
    }

    /**
     * 비밀번호 변경
     * 예: PUT /users/{id}/update-password
     * id는 path로 전달되고 body에는 oldPassword와 newPassword 포함
     */
    @PutMapping("/{id}/update-password")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> updatePassword(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdatePasswordRequest request
    ) {
        userService.changePassword(id, request.oldPassword(), request.newPassword());
        return ApiResult.ok();
        /* URI 설계 원칙: 리소스(유저)를 명확히 식별해야 함
        - /users → 모든 유저 (식별 불가)
        - /users/{id} → 특정 유저 (식별 가능)

        비밀번호 변경 API 설계 방식
        1) body(JSON)에 id 포함 → 비권장
        2) uri에 id 포함 (/users/{id}/update-password) → 명확
        3) 인증 객체(SecurityContext 등)에서 id 추출 → 권장
        */
    }

    /**
     * 유저 삭제
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ApiResult.ok();
    }
}