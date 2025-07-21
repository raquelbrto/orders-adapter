package com.devbrito.luizalabs.ordersadapter.service;

import com.devbrito.luizalabs.ordersadapter.domain.document.Order;
import com.devbrito.luizalabs.ordersadapter.domain.dto.UserOrdersResponseDTO;
import com.devbrito.luizalabs.ordersadapter.mapper.OrderMapper;
import com.devbrito.luizalabs.ordersadapter.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
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
}
