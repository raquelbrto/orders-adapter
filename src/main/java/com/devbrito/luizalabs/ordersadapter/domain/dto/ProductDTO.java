package com.devbrito.luizalabs.ordersadapter.domain.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDTO(Integer product_id, BigDecimal value) {
}
