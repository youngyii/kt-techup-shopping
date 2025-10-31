package com.kt.controller; // 클라이언트 요청 처리

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.kt.dto.UserCreateRequest;
import com.kt.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	// userservice를 DI 받아야 함
	// DI 받는 방식: *생성자 주입* 재할당을 금지함

	private final UserService userService;

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
