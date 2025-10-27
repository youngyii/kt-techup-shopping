package com.kt.dto; // 요청/응답용 데이터 전송 객체(Controller에서 JSON을 받을 때 사용)

import java.time.LocalDate;

// loginId, password, name, birthday(YYYY-mm-dd)
public record UserCreateRequest( // record: 불변 객체
	String loginId,
	String password,
	String name,
	LocalDate birthday
) {
}