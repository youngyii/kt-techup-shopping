package com.kt.domain.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ProductRequest {
    @Getter
    @AllArgsConstructor
    @Schema(name = "ProductRequest.Create") // Swagger DTO 이름 지정
    public static class Create {
        @NotBlank
        private String name;
        @NotNull
        private Long price;
        @NotNull
        private Long quantity;
    }

    @Getter
    @AllArgsConstructor
    @Schema(name = "ProductRequest.Update")
    public static class Update {
        @NotBlank
        private String name;
        @NotNull
        private Long price;
        @NotNull
        private Long quantity;
    }
    /* DTO의 기능을 응집시키는 방식 3가지
    1) 요청, 응답 각각 파일로 만드는 방식
        예: ProductCreateRequest.java, ProductUpdateRequest.java
    2) static class로 묶기 (지금 코드) -> 파일 개수 줄임, 관리 쉬움
    3) 인터페이스로 묶기
    */
}