package com.devbrito.luizalabs.ordersadapter.mapper;

import com.devbrito.luizalabs.ordersadapter.domain.document.Order;
import com.devbrito.luizalabs.ordersadapter.domain.document.Product;
import com.devbrito.luizalabs.ordersadapter.domain.dto.OrderParserDTO;
import com.devbrito.luizalabs.ordersadapter.domain.dto.OrderByUserDTO;
import com.devbrito.luizalabs.ordersadapter.domain.dto.OrderResponseDTO;
import com.devbrito.luizalabs.ordersadapter.domain.dto.UserOrdersResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderMapper {

    @Mapping(target = "orderId", source = "orderParserDTO.orderId")
    @Mapping(target = "userId", source = "orderParserDTO.userId")
    @Mapping(target = "userName", source = "orderParserDTO.userName")
    @Mapping(target = "products", source = "products")
    @Mapping(target = "total", source = "total")
    @Mapping(target = "date", source = "orderParserDTO.date")
    Order fromOrderParserDTO(OrderParserDTO orderParserDTO, List<Product> products, BigDecimal total);

    @Mapping(target = "order_id", source = "orderId")
    @Mapping(target = "total", source = "total")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "products", source = "products")
    @Mapping(target = "user_id", source = "userId")
    @Mapping(target = "name", source = "userName")
    OrderResponseDTO toOrderResponseDTO(Order order);

    @Mapping(target = "user_id", source = "userId")
    @Mapping(target = "name", expression = "java(orders.get(0).getUserName())")
    @Mapping(target = "orders", expression = "java(toOrderResponseByUserDTOList(orders))")
    UserOrdersResponseDTO toUserResponseDTO(Integer userId, List<Order> orders);

    @Mapping(target = "order_id", source = "orderId")
    @Mapping(target = "total", source = "total")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "products", expression = "java(productMapper.toProductDTOList(order.getProducts()))")
    OrderByUserDTO toOrderResponseByUserDTO(Order order);

    default List<OrderByUserDTO> toOrderResponseByUserDTOList(List<Order> orders) {
        return orders.stream().map(this::toOrderResponseByUserDTO).toList();
    }
}
