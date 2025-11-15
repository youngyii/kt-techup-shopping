package com.kt.controller;

import com.kt.domain.User;
import com.kt.dto.CustomPage;
import com.kt.dto.UserUpdateRequest;
import com.kt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// 관리자(Admin)용 유저 관리 API를 제공하는 Controller
// 기능: 유저 목록 조회, 상세 조회, 정보 수정, 삭제, 비밀번호 초기화
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    /**
     * 유저 목록 조회 (페이징+검색)
     * 예: /admin/users?page=1&size=10&keyword=kim
     * page : 조회할 페이지 번호 (기본값 1)
     * size : 한 페이지에 보여줄 개수 (기본값 10)
     * keyword : 검색어 (선택)
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomPage search(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword
    ) {
        return userService.search(page, size, keyword);
    }

    /**
     * 유저 상세 조회
     * 예: GET /admin/users/5
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User detail(@PathVariable Long id) {
        return userService.detail(id);
    }

    /**
     * 유저 정보 수정
     * 예: PUT /admin/users/5 (body: UserUpdateRequest JSON)
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable Long id, @RequestBody @Valid UserUpdateRequest request) {
        userService.update(id, request.name(), request.email(), request.mobile());
    }

    // TODO: 유저 삭제
    // TODO: 유저 비밀번호 초기화
}