package com.devbrito.luizalabs.ordersadapter.service;

import com.devbrito.luizalabs.ordersadapter.domain.document.Order;
import com.devbrito.luizalabs.ordersadapter.domain.document.Product;
import com.devbrito.luizalabs.ordersadapter.domain.dto.OrderResponseDTO;
import com.devbrito.luizalabs.ordersadapter.domain.dto.ProductDTO;
import com.devbrito.luizalabs.ordersadapter.exceptions.OrderNotFoundException;
import com.devbrito.luizalabs.ordersadapter.mapper.OrderMapper;
import com.devbrito.luizalabs.ordersadapter.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    private static final Order TEST_ORDER = Order.builder()
            .orderId(1)
            .userId(10)
            .userName("Medeiros")
            .products(List.of(Product.builder().productId(1).value(BigDecimal.valueOf(256.04)).build()))
            .total(BigDecimal.valueOf(256.04))
            .date(LocalDate.of(2023, 6, 15))
            .build();

    private static final OrderResponseDTO TEST_ORDER_RESPONSE_DTO = OrderResponseDTO.builder()
            .order_id(1)
            .user_id(10)
            .name("Medeiros")
            .products(List.of(ProductDTO.builder().product_id(1).value(BigDecimal.valueOf(256.04)).build()))
            .total(BigDecimal.valueOf(256.04))
            .date(LocalDate.of(2023, 6, 15))
            .build();

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Test
    void testListOrdersWithoutDateFilters() {
        when(orderRepository.findAll()).thenReturn(List.of(TEST_ORDER));
        when(orderMapper.toOrderResponseDTO(TEST_ORDER)).thenReturn(TEST_ORDER_RESPONSE_DTO);

        List<OrderResponseDTO> result = orderService.listOrders(null, null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(TEST_ORDER_RESPONSE_DTO, result.get(0));
    }

    @Test
    void testListOrdersWithDateFilter() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        when(orderRepository.findByDateBetween(startDate, endDate)).thenReturn(List.of(TEST_ORDER));
        when(orderMapper.toOrderResponseDTO(TEST_ORDER)).thenReturn(TEST_ORDER_RESPONSE_DTO);

        List<OrderResponseDTO> result = orderService.listOrders(startDate, endDate);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(TEST_ORDER_RESPONSE_DTO, result.get(0));
    }

    @Test
    void testListOrdersThrowsExceptionWhenStartDateIsAfterEndDate() {
        LocalDate startDate = LocalDate.of(2023, 12, 31);
        LocalDate endDate = LocalDate.of(2023, 1, 1);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            orderService.listOrders(startDate, endDate);
        });
        Assertions.assertEquals("Start date is after end date", exception.getMessage());
        verifyNoInteractions(orderRepository);
    }

    @Test
    void testFindByIdThrowsExceptionWhenOrderNotFound() {
        Integer orderId = 999;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        OrderNotFoundException exception = Assertions.assertThrows(OrderNotFoundException.class, () -> {
            orderService.findById(orderId);
        });

        verify(orderRepository).findById(orderId);
        verifyNoInteractions(orderMapper);
        Assertions.assertEquals("Order not found with id: " + orderId, exception.getMessage());
    }
}
