package com.kt.service;

import com.kt.domain.User;
import com.kt.dto.CustomPage;
import com.kt.dto.UserCreateRequest;
import com.kt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// 비즈니스 로직 처리 Service
// 기능: 회원가입, 로그인 ID 중복 체크, 비밀번호 변경,
//      유저 목록 조회, 상세 조회, 정보 수정
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * 회원가입
      */
    public void create(UserCreateRequest request) {
        var newUser = new User(
                userRepository.selectMaxId() + 1,
                request.loginId(),
                request.password(),
                request.name(),
                request.email(),
                request.mobile(),
                request.gender(),
                request.birthday(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

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
    public void changePassword(int id, String oldPassword, String password) {
        var user = userRepository.selectById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!user.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        if (oldPassword.equals(password)) {
            throw new IllegalArgumentException("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
        }

        userRepository.updatePassword(id, password);
    }

    /**
     * 유저 목록 조회 (페이징+검색)
     */
    public CustomPage search(int page, int size, String keyword) {
        var pair = userRepository.selectAll(page - 1, size, keyword);
        var pages = (int) Math.ceil((double) pair.getSecond() / size);

        return new CustomPage(
                pair.getFirst(),
                size,
                page,
                pages,
                pair.getSecond()
        );
    }

    /**
     * 유저 상세 조회
     */
    public User detail(Long id) {
        return userRepository.selectById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    /**
     * 유저 정보 수정
     */
    public void update(Long id, String name, String email, String mobile) {
        userRepository.selectById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        userRepository.updateById(id, name, email, mobile);
    }
}