package com.kt.repository;

import com.kt.domain.Gender;
import com.kt.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// DB 접근 Repository
// 기능: User 도메인 객체 저장/조회/수정/삭제, SQL 실행 및 결과 매핑
@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * 회원 저장
     */
    public void save(User user) {
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

    /**
     * 최대 ID 조회 (현재 ID 개수)
     */
    public Long selectMaxId() {
        var sql = "SELECT MAX(id) FROM MEMBER";
        var maxId = jdbcTemplate.queryForObject(sql, Long.class);

        return maxId == null ? 0L : maxId;
    }

    /**
     * 로그인 ID 중복 체크
     */
    public boolean existsByLoginId(String loginId) {
        var sql = "SELECT EXISTS (SELECT id FROM MEMBER WHERE loginId = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, loginId));
        /* 아이디 중복 체크 방법
        1) count로 체크 -> 데이터가 많으면 비효율적
        2) unique 제약조건 + 예외 처리 -> 중복 시 DataIntegrityViolationException
        3) exists로 존재 여부 체크 -> boolean으로 값 존재 여부 확인 가능
        */
    }

    /**
     * 비밀번호 변경
     */
    public void updatePassword(long id, String password) {
        var sql = "UPDATE MEMBER SET password = ? WHERE id = ?";

        jdbcTemplate.update(sql, password, id);
    }

    /**
     * ID 존재 여부 확인
     */
    public boolean existsById(long id) {
        var sql = "SELECT EXISTS (SELECT id FROM MEMBER WHERE id = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, id));
    }

    /**
     * ID로 유저 조회
     */
    public Optional<User> selectById(long id) {
        var sql = "SELECT * FROM MEMBER WHERE id = ?";
        var list = jdbcTemplate.query(sql, rowMapper(), id); // DB 결과를 List<User>로 반환

        return list.stream().findFirst();
    }

    /**
     * 유저 목록 조회 (페이징+검색)
     */
    public Pair<List<User>, Long> selectAll(int page, int size, String keyword) {
        var sql = "SELECT * FROM MEMBER WHERE name LIKE CONCAT('%', ? , '%') LIMIT ? OFFSET ?"; // offset: 건너뛸 레코드 수 ((page - 1) * size)
        var users = jdbcTemplate.query(sql, rowMapper(), keyword, size, page * size);

        var countSql = "SELECT COUNT(*) FROM MEMBER WHERE name LIKE CONCAT('%', ? , '%')";  // 키워드를 포함
        var totalElements = jdbcTemplate.queryForObject(countSql, Long.class, keyword);

        return Pair.of(users, totalElements);
    }

    /**
     * ID로 유저 정보 수정
     */
    public void updateById(Long id, String name, String email, String mobile) {
        var sql = "UPDATE MEMBER SET name = ?, email = ?, mobile = ?, updatedAt = ? WHERE id = ?";

        jdbcTemplate.update(sql, name, email, mobile, LocalDateTime.now(), id);
    }

    /**
     * ResultSet → User 객체로 매핑
     */
    private RowMapper<User> rowMapper() {
        return (rs, rowNum) -> mapToUser(rs);
        // 람다가 단일 실행문이면 {}와 return 생략 - (rs, rowNum) -> {return mapToUser(rs);}와 동일
    }
    private User mapToUser(ResultSet rs) throws SQLException {
        System.out.println("mapToUser called");
        return new User(
                rs.getLong("id"),
                rs.getString("loginId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("mobile"),
                Gender.valueOf(rs.getString("gender")),
                rs.getObject("birthday", LocalDate.class),
                rs.getObject("createdAt", LocalDateTime.class),
                rs.getObject("updatedAt", LocalDateTime.class)
        );
    }
}