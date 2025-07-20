package com.devbrito.luizalabs.ordersadapter.domain.dto;

import com.devbrito.luizalabs.ordersadapter.domain.document.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderDTO(Long orderId,
                       BigDecimal total,
                       LocalDate date,
                       List<Product> products) {
}
