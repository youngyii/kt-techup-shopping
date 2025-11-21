package com.kt.controller.user;

import com.kt.common.ApiResult;
import com.kt.common.Paging;
import com.kt.common.SwaggerAssistance;
import com.kt.dto.user.UserResponse;
import com.kt.dto.user.UserUpdateRequest;
import com.kt.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// 관리자(Admin)용 유저 관리 API를 제공하는 Controller
// 기능: 유저 목록 조회, 상세 조회, 정보 수정, 삭제, 비밀번호 초기화
@Tag(name = "User")
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController extends SwaggerAssistance {
    private final UserService userService;

    /**
     * 유저 목록 조회 (페이징+검색)
     * 예: /admin/users?keyword=kim&page=1&size=10
     * page : 조회할 페이지 번호 (기본값 1)
     * size : 한 페이지에 보여줄 개수 (기본값 10)
     * keyword : 검색어 (선택)
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Page<UserResponse.Search>> search(
            @RequestParam(required = false)
            String keyword,
            Paging paging
    ) {
        var search = userService.search(paging.toPageable(), keyword)
                .map(user -> new UserResponse.Search(
                        user.getId(),
                        user.getName(),
                        user.getCreatedAt()
                ));

        return ApiResult.ok(search);
        // Pageable: interface / PageRequest: 구현체
    }

    /**
     * 유저 상세 조회
     * 예: GET /admin/users/5
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserResponse.Detail> detail(@PathVariable Long id) {
        var user = userService.detail(id);

        return ApiResult.ok(new UserResponse.Detail(
                user.getId(),
                user.getName(),
                user.getEmail()
        ));
    }

    /**
     * 유저 정보 수정
     * 예: PUT /admin/users/5 (body: UserUpdateRequest JSON)
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> update(@PathVariable Long id, @RequestBody @Valid UserUpdateRequest request) {
        userService.update(id, request.name(), request.email(), request.mobile());

        return ApiResult.ok();
    }

    // TODO: 유저 삭제
    // TODO: 유저 비밀번호 초기화
}