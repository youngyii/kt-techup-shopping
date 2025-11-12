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

		var sql = """
			INSERT INTO MEMBER (
			                    id,
			                    loginId, 
			                    password, 
			                    name, 
			                    birthday,
			                    mobile,
			                    email,
			                    gender,
			                    createdAt,
			                    updatedAt
			                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
			""";

		jdbcTemplate.update(
			sql,
			user.getId(),
			user.getLoginId(),
			user.getPassword(),
			user.getName(),
			user.getBirthday(),
			user.getMobile(),
			user.getEmail(),
			user.getGender().name(),
			user.getCreatedAt(),
			user.getUpdatedAt()
		);
	}

	public Long selectMaxId() {
		var sql = "SELECT MAX(id) FROM MEMBER";
		var maxId = jdbcTemplate.queryForObject(sql, Long.class);

		return maxId == null ? 0L : maxId;
	}

    /* 아이디 중복 체크 방법 3가지
    1. count로 체크 -> 데이터가 많으면 비효율적
    2. unique 제약조건 + 예외 처리 -> 중복 시 DataIntegrityViolationException
    3. exists로 존재 여부 체크 -> boolean으로 값 존재 여부 확인 가능
     */

     public boolean existsByLoginId(String loginId) {
         var sql = "SELECT EXISTS (SELECT id FROM MEMBER WHERE loginId = ?)";

         return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, loginId));
     }

    public void updatePassword(int id, String password) {
        // UPDATE {table} SET {column} = {value}, {column} = {value} WHERE {condition}
        var sql = "UPDATE MEMBER SET password = ? WHERE id = ?";

        jdbcTemplate.update(sql, password, id);
    }

    public boolean existsById(int id) {
         var sql = "SELECT EXISTS (SELECT id FROM MEMBER WHERE id = ?)";

         return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, id));
    }
}