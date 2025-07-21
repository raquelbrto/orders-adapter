package com.devbrito.luizalabs.ordersadapter.service;

import com.devbrito.luizalabs.ordersadapter.domain.document.Order;
import com.devbrito.luizalabs.ordersadapter.domain.document.Product;
import com.devbrito.luizalabs.ordersadapter.domain.dto.OrderParserDTO;
import com.devbrito.luizalabs.ordersadapter.mapper.OrderMapper;
import com.devbrito.luizalabs.ordersadapter.utils.FileParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FileProcessingService {

    private final FileParser fileParser;

    private final OrderMapper orderMapper;

    public FileProcessingService(FileParser fileParser, OrderMapper orderMapper) {
        this.fileParser = fileParser;
        this.orderMapper = orderMapper;
    }
}
