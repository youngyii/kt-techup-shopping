package com.kt.dto; // 요청/응답용 데이터 전송 객체(Controller에서 JSON을 받을 때 사용)

import java.time.LocalDate;
import com.kt.domain.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

// bean validation 기능을 통해 유효성 검사
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