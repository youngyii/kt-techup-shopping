package com.kt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kt.domain.User;

// JPA Repository
// 기능: CRUD 등 기본 메서드 자동 제공, JPQL 작성 지원
public interface UserRepository extends JpaRepository<User, Long> {
    /* <T, ID>
    T : 엔티티 타입(User)
    ID : 엔티티 PK 타입(Long)

    JPA 쿼리 작성 방식 3가지
    1) Query Method - 메서드 이름 기반 자동 쿼리 생성
    2) JPQL - SQL이 아닌 객체 기반 쿼리
    3) Native SQL
     */

    // Query Method
    Boolean existsByLoginId(String loginId);

    // JPQL
    @Query("""
            SELECT exists (SELECT u FROM User u WHERE u.loginId = ?1)
            """)
    Boolean existsByLoginIdJPQL(String loginId);
}