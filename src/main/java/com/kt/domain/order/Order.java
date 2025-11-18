package com.kt.domain.order;

import com.kt.common.BaseEntity;
import com.kt.domain.orderproduct.OrderProduct;
import com.kt.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private String receiverName;
    private String receiverAdress;
    private String receiverMobile;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime deliveredAt;

    @ManyToOne // N(주문) : 1(회원)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts = new ArrayList<>();

    // 주문생성
    // 주문상태변경
    // 주문생성완료재고차감
    // 배송받는사람정보변경
    // 주문취소
}