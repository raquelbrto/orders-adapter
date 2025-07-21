package com.devbrito.luizalabs.ordersadapter.controller;

import com.devbrito.luizalabs.ordersadapter.domain.dto.OrderResponseDTO;
import com.devbrito.luizalabs.ordersadapter.domain.dto.UserOrdersResponseDTO;
import com.devbrito.luizalabs.ordersadapter.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    ResponseEntity<List<UserOrdersResponseDTO>> saveFile(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<UserOrdersResponseDTO> orders = orderService.saveAll(file);
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping("/{orderId}")
    ResponseEntity<OrderResponseDTO> findById(@PathVariable("orderId") Integer orderId) {
        return ResponseEntity.ok().body(orderService.findById(orderId));
    }

    @GetMapping()
    public ResponseEntity<List<OrderResponseDTO>> listOrders(
            @RequestParam(value = "start_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "end_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseEntity.ok().body(orderService.listOrders(startDate, endDate));
    }
}
