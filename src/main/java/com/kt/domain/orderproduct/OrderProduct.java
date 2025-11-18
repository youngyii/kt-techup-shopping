package com.kt.domain.orderproduct;

import com.kt.common.BaseEntity;
import com.kt.domain.order.Order;
import com.kt.domain.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Getter
@Entity
public class OrderProduct extends BaseEntity {
    private Long quentity;
    @OneToOne
    private Order order;
    @ManyToOne
    private Product product;
}