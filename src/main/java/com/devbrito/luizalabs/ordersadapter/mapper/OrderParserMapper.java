package com.devbrito.luizalabs.ordersadapter.mapper;

import com.devbrito.luizalabs.ordersadapter.domain.document.Order;
import com.devbrito.luizalabs.ordersadapter.domain.dto.OrderParserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderParserMapper {

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "products", expression = "java(java.util.Collections.singletonList(orderParserDTO.product()))")
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "date", source = "date")
    Order toOrder(OrderParserDTO orderParserDTO);

    List<Order> toOrderList(List<OrderParserDTO> orderParserDTOs);
}
