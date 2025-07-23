package com.devbrito.luizalabs.ordersadapter.domain.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserOrdersResponseDTO(Integer user_id,
                                    String name,
                                    List<OrderByUserDTO> orders) {
}
