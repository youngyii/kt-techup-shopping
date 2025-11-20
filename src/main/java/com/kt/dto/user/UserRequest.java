package com.kt.dto.user;

import com.kt.domain.user.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

public class UserRequest {
    @Schema(name = "UserRequest.Create") // Swagger DTO 이름 지정
    public record Create(
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

    @Schema(name = "UserRequest.Update")
    public record Update(
            @NotBlank
            String name,
            @NotBlank
            @Pattern(regexp = "^(0\\d{1,2})-(\\d{3,4})-(\\d{4})$")
            String mobile,
            @NotBlank
            @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
            String email
    ) {
    }
    /* DTO의 기능을 응집시키는 방식 3가지
    1) 요청, 응답 각각 파일로 만드는 방식
        예: ProductCreateRequest.java, ProductUpdateRequest.java
    2) static class로 묶기 -> 파일 개수 줄임, 관리 쉬움
    3) 인터페이스로 묶기 (지금 코드) record -> 완전 불변으로 정의
    */
}