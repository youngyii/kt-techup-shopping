package com.kt.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

// 유저 정보를 DB와 매핑하는 JPA 엔티티
// 기능: 유저 데이터 보관, 비밀번호 변경, 정보 수정 등 도메인 행위 제공
@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String mobile;
    @Enumerated(EnumType.STRING)
    /* ORDINAL - 열거형 상수의 순서(인덱스)를 숫자로 저장
     * STRING - 열거형 상수의 이름을 문자열로 저장 */
    private Gender gender;
    private LocalDate birthday;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(String loginId, String password, String name, String email, String mobile, Gender gender,
                LocalDate birthday, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.birthday = birthday;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void update(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }
}