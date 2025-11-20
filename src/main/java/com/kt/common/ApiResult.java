package com.kt.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResult<T> { // <T>: 제네릭 타입 매개변수
    private String code;    // 응답 코드
    private String message; // 응답 메시지
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;         // 실제 데이터

    // 성공 응답 생성 (데이터 없는 경우)
    public static ApiResult<Void> ok() {
        return ApiResult.of("ok", "성공", null);
    }

    // 성공 응답 생성 (데이터 있는 경우)
    public static <T> ApiResult<T> ok(T data) {
        return ApiResult.of("ok", "성공", data);
    }

    // 내부 사용 팩토리 메서드
    public static <T> ApiResult<T> of(String code, String message, T data) {
        return new ApiResult<>(code, message, data);
    }
}