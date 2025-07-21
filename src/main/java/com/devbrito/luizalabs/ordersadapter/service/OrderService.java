package com.devbrito.luizalabs.ordersadapter.service;

import com.devbrito.luizalabs.ordersadapter.domain.document.Order;
import com.devbrito.luizalabs.ordersadapter.domain.dto.OrderResponseDTO;
import com.devbrito.luizalabs.ordersadapter.domain.dto.UserOrdersResponseDTO;
import com.devbrito.luizalabs.ordersadapter.exceptions.InvalidParamDateException;
import com.devbrito.luizalabs.ordersadapter.exceptions.OrderNotFoundException;
import com.devbrito.luizalabs.ordersadapter.mapper.OrderMapper;
import com.devbrito.luizalabs.ordersadapter.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final FileProcessingService fileProcessingService;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, FileProcessingService fileProcessingService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.fileProcessingService = fileProcessingService;
    }

    public List<UserOrdersResponseDTO> saveAll(MultipartFile file) throws IOException {
        List<Order> orders = fileProcessingService.processFile(file);

        orderRepository.saveAll(orders);

        return orders.stream()
                .collect(Collectors.groupingBy(Order::getUserId, LinkedHashMap::new, Collectors.toList()))
                .entrySet().stream()
                .map(entry -> orderMapper.toUserResponseDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    public OrderResponseDTO findById(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        return orderMapper.toOrderResponseDTO(order);
    }

    public List<OrderResponseDTO> listOrders(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return filterByDate(startDate, endDate);
        }

        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toOrderResponseDTO).toList();
    }

    private List<OrderResponseDTO> filterByDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            log.error("Start date is after end date");
            throw new InvalidParamDateException("Start date is after end date");
        }

        List<Order> orders = orderRepository.findByDateBetween(Optional.of(startDate).orElse(LocalDate.MIN),
                Optional.of(endDate).orElse(LocalDate.MAX));

        return orders.stream().map(orderMapper::toOrderResponseDTO).toList();
    }
}
