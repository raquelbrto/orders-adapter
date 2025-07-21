package com.devbrito.luizalabs.ordersadapter.domain.dto;

import com.devbrito.luizalabs.ordersadapter.domain.document.Product;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record OrderParserDTO(Integer userId,
                             String userName,
                             Integer orderId,
                             Product product,
                             LocalDate date) {
}
