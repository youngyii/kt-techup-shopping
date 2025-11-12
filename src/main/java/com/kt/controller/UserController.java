package com.kt.controller; // 클라이언트 요청 처리

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kt.dto.UserCreateRequest;
import com.kt.dto.UserUpdatePasswordRequest;
import com.kt.service.UserService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "유효성 검사 실패"),
        @ApiResponse(responseCode = "500", description = "서버 에러 - 백엔드에 바로 문의 바랍니다.")
})
public class UserController {
	// userservice를 DI 받아야 함
	// DI 받는 방식: *생성자 주입* 재할당을 금지함

	private final UserService userService;

    // API 문서화
    // 1. Swagger
    // 장점: UI가 예쁘다, 어노테이션 기반이라 작성이 쉽다
    // 단점: 프로덕션 코드에 Swagger 관련 어노테이션이 존재, 코드가 더러워지고 길어지고 유지보수 힘듦
    // 2. RestDocs
    // 장점: 프로덕션 코드 침범 없다, 신뢰할 수 있다
    // 단점: UI가 안 예쁘다, 테스트 코드로 작성해야 해서 시간이 오래걸린다

	@ResponseStatus(HttpStatus.CREATED)

	// loginId, password, name, birthday
	// JSON 형태의 body에 담겨서 POST 요청으로 /users로 들어오면
	// @RequestBody를보고 jacksonObjectMapper가 동작해서 JSON을 읽어서 DTO(UserCreateRequest)로 변환
	public void create(@Valid @RequestBody UserCreateRequest request) {
		// jackson object mapper -> json to dto를 매핑
		userService.create(request); // 요청 들어오면 userService.create()로 위임
	}

    /*
    /users/duplicate-login-id?loginId=ktuser
    GET에서 쓰는 queryString(?loginId=ktuser -> 파라미터 loginId를 ktuser 값으로 서버에 전달)
    @RequestParam의 속성은 기본값이 required = true
    IlleagalArgumentException 발생 시 400 에러(loginId가 없거나 비정상이면)
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Boolean isDuplicateLoginId(@RequestParam String loginId) {
        return userService.isDuplicateLoginId(loginId);
    }

    /* URI 설계 원칙: 리소스(유저)를 명확히 식별할 수 있어야 한다.
    예) /users → 모든 유저 (식별 불가)
        /users/{id} → 특정 유저 (식별 가능)

    비밀번호 변경 API 설계 방법
    1. body(JSON)에 id 포함 → REST스럽지 않음
    2. uri에 id 포함 (/users/{id}/update-password) → 명확함
    3. 인증 객체(SecurityContext 등)에서 id 추출 → 권장
     */
    @PutMapping("/{id}/update-password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(
            @PathVariable Integer id,
            @RequestBody @Valid UserUpdatePasswordRequest request
    ) {
        userService.changePassword(id, request.oldPassword(), request.newPassword());
    }
}