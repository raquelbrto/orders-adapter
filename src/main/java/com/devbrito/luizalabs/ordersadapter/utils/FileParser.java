package com.devbrito.luizalabs.ordersadapter.utils;

import com.devbrito.luizalabs.ordersadapter.domain.document.Product;
import com.devbrito.luizalabs.ordersadapter.domain.dto.OrderParserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileParser {

    private static final int userIdBeginIndex = 0;
    private static final int userIdEndIndex = 10;

    private static final int userNameBeginIndex = 10;
    private static final int userNameEndIndex = 55;

    private static final int orderIdBeginIndex = 55;
    private static final int orderIdEndIndex = 65;

    private static final int productIdBeginIndex = 65;
    private static final int productIdEndIndex = 75;

    private static final int valueBeginIndex = 75;
    private static final int valueEndIndex = 87;

    private static final int dateBeginIndex = 87;
    private static final int dateEndIndex = 95;

    public Integer getId(String line, int beginIndex, int endIndex) {
        String idStr = line.substring(beginIndex, endIndex);
        return Integer.parseInt(idStr);
    }

    public String getUserName(String line, int beginIndex, int endIndex) {
        return line.substring(beginIndex, endIndex).trim();
    }

    public Product getProduct(String line) {
        return Product.builder()
                .productId(getProductId(line, productIdBeginIndex, productIdEndIndex))
                .value(getValue(line, valueBeginIndex, valueEndIndex))
                .build();
    }

    public Integer getProductId(String line, int beginIndex, int endIndex) {
        String productIdStr = line.substring(beginIndex, endIndex);
       
        return Integer.parseInt(productIdStr);
    }

    public BigDecimal getValue(String line, int beginIndex, int endIndex) {
        String valueStr = line.substring(beginIndex, endIndex).trim();
        return new BigDecimal(valueStr).setScale(2, RoundingMode.HALF_UP);
    }

    public LocalDate getDate(String line, int beginIndex, int endIndex) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(line.substring(beginIndex, endIndex), formatter);
    }

    public List<OrderParserDTO> parseFile(MultipartFile orderFile) throws IOException {
        if (orderFile.isEmpty() || orderFile.getSize() == 0L) {
            throw new IllegalArgumentException("File is empty or size is 0");
        }

        List<OrderParserDTO> orderParserDTO = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(orderFile.getInputStream()));

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            OrderParserDTO order = new OrderParserDTO(
                getId(line, userIdBeginIndex, userIdEndIndex),
                getUserName(line, userNameBeginIndex, userNameEndIndex),
                getId(line, orderIdBeginIndex, orderIdEndIndex),
                getProduct(line),
                getDate(line, dateBeginIndex, dateEndIndex)
            );
            orderParserDTO.add(order);
        }
        return orderParserDTO;
    }
}
