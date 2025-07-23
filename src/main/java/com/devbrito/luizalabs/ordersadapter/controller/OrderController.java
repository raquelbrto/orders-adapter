package com.devbrito.luizalabs.ordersadapter.controller;

import com.devbrito.luizalabs.ordersadapter.domain.dto.OrderResponseDTO;
import com.devbrito.luizalabs.ordersadapter.domain.dto.UserOrdersResponseDTO;
import com.devbrito.luizalabs.ordersadapter.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Orders", description = "Operations related to the order")
@RestController
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
            summary = "Save orders from file",
            description = "Creates orders based on the data from the uploaded file",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(
                                    type = "object",
                                    requiredProperties = {"file"}
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Orders processed successfully",
                            content = @Content(schema = @Schema(type = "array", implementation = UserOrdersResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
                    @ApiResponse(responseCode = "400", description = "File is empty", content = @Content)
            }
    )
    @PostMapping
    ResponseEntity<List<UserOrdersResponseDTO>> save(@RequestParam("file") MultipartFile file) throws IOException {
        List<UserOrdersResponseDTO> orders = orderService.saveAll(file);
        return ResponseEntity.ok().body(orders);
    }

    @Operation(
            summary = "Find order by ID",
            description = "Searches for an order by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returns the order",
                            content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Order not found with the given ID", content = @Content)
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<OrderResponseDTO> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(orderService.findById(id));
    }

    @Operation(
            summary = "List all orders",
            description = "Lists all orders. Filtering by a date range (start_date and end_date) is optional.",
            parameters = {
                    @Parameter(
                            name = "start_date",
                            description = "Start date for filtering orders (format: yyyy-MM-dd)",
                            required = false,
                            example = "2021-01-01"
                    ),
                    @Parameter(
                            name = "end_date",
                            description = "End date for filtering orders (format: yyyy-MM-dd)",
                            required = false,
                            example = "2021-12-31"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returns the list of orders",
                            content = @Content(schema = @Schema(type = "array", implementation = OrderResponseDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
                    @ApiResponse(responseCode = "400", description = "The start date cannot be after the end date", content = @Content)
            }
    )
    @GetMapping()
    public ResponseEntity<List<OrderResponseDTO>> listOrders(
            @RequestParam(value = "start_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "end_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseEntity.ok().body(orderService.listOrders(startDate, endDate));
    }
}
