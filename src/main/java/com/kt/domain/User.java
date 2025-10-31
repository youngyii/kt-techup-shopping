package com.kt.domain; // 비즈니스에서 다루는 유저 모델 - DB에 저장될 형태

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	private LocalDate createdAt;
	private LocalDate updatedAt;
}
