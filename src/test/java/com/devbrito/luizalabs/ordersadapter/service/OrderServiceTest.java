package com.devbrito.luizalabs.ordersadapter.service;

import com.devbrito.luizalabs.ordersadapter.domain.document.Order;
import com.devbrito.luizalabs.ordersadapter.domain.document.Product;
import com.devbrito.luizalabs.ordersadapter.domain.dto.OrderByUserDTO;
import com.devbrito.luizalabs.ordersadapter.domain.dto.OrderResponseDTO;
import com.devbrito.luizalabs.ordersadapter.domain.dto.ProductDTO;
import com.devbrito.luizalabs.ordersadapter.domain.dto.UserOrdersResponseDTO;
import com.devbrito.luizalabs.ordersadapter.mapper.OrderMapper;
import com.devbrito.luizalabs.ordersadapter.mapper.ProductMapper;
import com.devbrito.luizalabs.ordersadapter.repository.OrderRepository;
import com.devbrito.luizalabs.ordersadapter.utils.FileParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    FileProcessingService fileProcessingService;

    @Mock
    OrderMapper orderMapper;

    @Mock
    ProductMapper productMapper;

    @Mock
    FileParser fileParser;

    @BeforeEach
    void setUp() {
        List<UserOrdersResponseDTO> userOrders = new ArrayList<>();
        List<ProductDTO> products = new ArrayList<>();
        List<OrderByUserDTO> orders = new ArrayList<>();
        ProductDTO product1 = ProductDTO.builder().product_id(3).value(BigDecimal.valueOf(817.13)).build();
        ProductDTO product2 = ProductDTO.builder().product_id(2).value(BigDecimal.valueOf(1633.1)).build();
        LocalDate date = LocalDate.of(2021, 6, 12);
        BigDecimal total = BigDecimal.valueOf(2450.23);
        products.add(product1);
        products.add(product2);
        OrderByUserDTO order = OrderByUserDTO.builder().order_id(877).total(total).date(date).products(products).build();
        orders.add(order);
        UserOrdersResponseDTO userOrder = UserOrdersResponseDTO.builder().user_id(80).name("Tabitha Kuhn").orders(orders).build();

        userOrders.add(userOrder);
    }

    @Test
    void testListOrdersWithDateFilter() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        Product product = Product.builder()
                .productId(1)
                .value(BigDecimal.valueOf(256.04))
                .build();

        Order order = Order.builder()
                .orderId(1)
                .userId(10)
                .userName("Medeiros")
                .products(List.of(product))
                .total(BigDecimal.valueOf(256.04))
                .date(LocalDate.of(2023, 6, 15))
                .build();

        OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
                .order_id(1)
                .user_id(10)
                .name("Medeiros")
                .products(List.of(ProductDTO.builder().product_id(1).value(BigDecimal.valueOf(256.04)).build()))
                .total(BigDecimal.valueOf(256.04))
                .date(LocalDate.of(2023, 6, 15))
                .build();

        when(orderRepository.findByDateBetween(startDate, endDate)).thenReturn(List.of(order));
        when(orderMapper.toOrderResponseDTO(order)).thenReturn(orderResponseDTO);

        List<OrderResponseDTO> result = orderService.listOrders(startDate, endDate);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(orderResponseDTO, result.get(0));
    }

    @Test
    void testListOrdersWithDateFilterThrowsExceptionWhenStartDateAfterEndDate() {
        LocalDate startDate = LocalDate.of(2023, 12, 31);
        LocalDate endDate = LocalDate.of(2023, 1, 1);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            orderService.listOrders(startDate, endDate);
        });
        Assertions.assertEquals("Start date is after end date", exception.getMessage());
    }

    @Test
    void testListOrdersWithDateFilterThrowsExceptionWhenEndDateBeforeStartDate() {
        LocalDate startDate = LocalDate.of(2023, 6, 15);
        LocalDate endDate = LocalDate.of(2023, 1, 1);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            orderService.listOrders(startDate, endDate);
        });
        Assertions.assertEquals("Start date is after end date", exception.getMessage());
    }
}
