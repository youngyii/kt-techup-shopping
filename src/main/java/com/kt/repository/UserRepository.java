package com.kt.repository; // 데이터베이스 접근(SQL 실행)

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import com.kt.domain.User;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {
	private final JdbcTemplate jdbcTemplate; // SQL 실행을 도움

	public void save(User user) {
		// 서비스에서 dto를 User 도메인 객체(비지니스 모델)로 바꾼 후 전달
		var sql = "INSERT INTO MEMBER (loginId, password, name, birthday VALUES (?, ?, ?, ?);";

		jdbcTemplate.update(sql, user.getLoginId(), user.getPassword(), user.getName(), user.getBirthday());
		System.out.println("save user: " + user.toString());
	}

	public Long selectMaxId() {
		var sql = "SELECT MAX(id) FROM MEMBER";

		return jdbcTemplate.queryForObject(sql, Long.class);
	}
}
