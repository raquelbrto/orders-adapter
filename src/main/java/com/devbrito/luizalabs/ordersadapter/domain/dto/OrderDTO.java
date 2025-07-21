package com.devbrito.luizalabs.ordersadapter.domain.dto;

import com.devbrito.luizalabs.ordersadapter.domain.document.Product;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Builder
public record OrderDTO(Integer orderId,
                       BigDecimal total,
                       LocalDate date,
                       List<Product> products) {
}
