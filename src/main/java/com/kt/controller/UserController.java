package com.kt.controller; // 클라이언트 요청 처리

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.kt.dto.UserCreateRequest;
import com.kt.service.UserService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패"),
            @ApiResponse(responseCode = "500", description = "서버 에러 - 백엔드에 바로 문의 바랍니다.")
    })
	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)

	// loginId, password, name, birthday
	// JSON 형태의 body에 담겨서 POST 요청으로 /users로 들어오면
	// @RequestBody를보고 jacksonObjectMapper가 동작해서 JSON을 읽어서 DTO(UserCreateRequest)로 변환
	public void create(@Valid @RequestBody UserCreateRequest request) {
		// jackson object mapper -> json to dto를 매핑
		userService.create(request); // 요청 들어오면 userService.create()로 위임
	}
}
