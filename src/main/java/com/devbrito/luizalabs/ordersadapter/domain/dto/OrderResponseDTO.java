package com.devbrito.luizalabs.ordersadapter.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Builder
public record OrderResponseDTO(Integer order_id,
                               BigDecimal total,

                               @JsonSerialize(using = LocalDateSerializer.class)
                               @JsonDeserialize(using = LocalDateDeserializer.class)
                               LocalDate date,
                               List<ProductDTO> products,
                               Integer user_id,
                               String name) {
}
