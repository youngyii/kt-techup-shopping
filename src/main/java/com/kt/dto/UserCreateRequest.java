package com.kt.dto;

import com.kt.domain.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

/**
 * 회원가입 요청 DTO
 */
// Bean Validation 적용으로 요청 데이터 유효성 검사
public record UserCreateRequest( // record: 불변 객체
        @NotBlank
        String loginId,

        @NotBlank
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^])[A-Za-z\\d!@#$%^]{8,}$")
        String password,

        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        String email,

        @NotBlank
        @Pattern(regexp = "^(0\\d{1,2})-(\\d{3,4})-(\\d{4})$")
        String mobile,

        @NotNull
        Gender gender,

        @NotNull
        LocalDate birthday
) {
}