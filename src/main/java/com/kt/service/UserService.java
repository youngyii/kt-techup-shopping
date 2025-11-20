package com.kt.service;

import com.kt.domain.user.User;
import com.kt.dto.user.UserCreateRequest;
import com.kt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

// 비즈니스 로직 처리 Service
// 기능: 회원가입, 로그인 ID 중복 체크, 비밀번호 변경,
//      유저 목록 조회, 상세 조회, 정보 수정, 유저 삭제
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    //private final UserJDBCRepository userJDBCRepository;
    private final UserRepository userRepository;

    /**
     * 회원가입
     */
    public void create(UserCreateRequest request) {
        var newUser = new User(request.loginId(), request.password(), request.name(), request.email(), request.mobile(), request.gender(), request.birthday(), LocalDateTime.now(), LocalDateTime.now());

        userRepository.save(newUser);
    }

    /**
     * 로그인 ID 중복 체크
     */
    public boolean isDuplicateLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    /**
     * 비밀번호 변경
     */
    public void changePassword(Long id, String oldPassword, String password) {
        var user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!user.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        if (oldPassword.equals(password)) {
            throw new IllegalArgumentException("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
        }

        user.changePassword(password);
    }

    /**
     * 유저 목록 조회 (페이징+검색)
     */
    public Page<User> search(Pageable pageable, String keyword) {
        return userRepository.findAllByNameContaining(keyword, pageable);
    }

    /**
     * 유저 상세 조회
     */
    public User detail(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    /**
     * 유저 정보 수정
     */
    public void update(Long id, String name, String email, String mobile) {
        var user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        user.update(name, email, mobile);
    }

    /**
     * 유저 삭제
     */
    public void delete(Long id) {
        // Hard Delete: DB에서 완전 삭제 (복구 불가)
        userRepository.deleteById(id);

        /* Soft Delete: deleted 플래그로 관리 (복구 가능)
         * 엔터티에 추가 필요: @SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
         *                   @Where(clause = "deleted = false") // 삭제 데이터 자동 필터링
         * User user = userRepository.findById(id).orElseThrow(...);
         * user.setDeleted(true);
         * userRepository.save(user);
         */
    }
}