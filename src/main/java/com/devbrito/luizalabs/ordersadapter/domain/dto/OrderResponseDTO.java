package com.devbrito.luizalabs.ordersadapter.domain.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Builder
public record OrderResponseDTO(Integer order_id,
                               BigDecimal total,
                               LocalDate date,
                               List<ProductDTO> products,
                               Integer user_id,
                               String name) {
}
