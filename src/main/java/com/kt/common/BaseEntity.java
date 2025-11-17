package com.kt.common;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

// 유저 정보를 DB와 매핑하는 JPA 엔티티
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}