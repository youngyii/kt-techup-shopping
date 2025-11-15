package com.kt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

// 비즈니스 로직에서 사용하는 유저 모델, DB 저장용 객체
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String mobile;
    private Gender gender;
    private LocalDate birthday;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}