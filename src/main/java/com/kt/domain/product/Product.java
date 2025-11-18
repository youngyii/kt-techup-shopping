package com.kt.domain.product;

import com.kt.common.BaseEntity;
import com.kt.domain.orderproduct.OrderProduct;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product extends BaseEntity {
    private String name;
    private Long price;
    private Long stock;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProducts = new ArrayList<>();

    // TODO: 생성
    // TODO: 수정
    // TODO: 삭제
    // TODO: 조회 (리스트, 단건)
    // TODO: 상태 변경
    // TODO: 재고 수량 감소
    // TODO: 재고 수량 증가
}