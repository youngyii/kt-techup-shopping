package com.kt.dto;

import com.kt.domain.User;

import java.util.List;

/**
 * 페이징 처리를 위한 DTO
 */
/* 프론트엔드에서 목록 데이터를 페이지 단위로 보여줄 때 필요한 정보들을 포함
* users: 현재 페이지에 표시할 사용자 목록 데이터
* size: 한 페이지에 보여줄 데이터 개수 (limit)
* page: 조회할 페이지 번호
* pages: 전체 페이지 수 (totalElements / size 로 계산)
* totalElements: 전체 데이터 개수 (count)
*/
public record CustomPage(
        List<User> users,
        int size,
        int page,
        int pages,
        long totalElements
) {
}