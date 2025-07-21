package com.devbrito.luizalabs.ordersadapter.utils;

import com.devbrito.luizalabs.ordersadapter.domain.document.Product;
import com.devbrito.luizalabs.ordersadapter.domain.dto.OrderParserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class FileParserTest {

    @InjectMocks
    private FileParser fileParser;

    @Test
    void testParseFileSuccess() throws IOException {
        Path path = Paths.get("src/test/resources/orders.txt");
        MultipartFile file = new MockMultipartFile("orders.txt", "orders.txt", "text/plain", Files.readAllBytes(path));

        List<OrderParserDTO> orders = fileParser.parseFile(file);

        Product product = new Product(111, BigDecimal.valueOf(256.04));

        Assertions.assertEquals(2, orders.get(0).userId());
        Assertions.assertEquals("Medeiros", orders.get(0).userName());
        Assertions.assertEquals(12345, orders.get(0).orderId());
        Assertions.assertEquals(product, orders.get(0).product());
        Assertions.assertEquals(LocalDate.parse("2020-12-01"), orders.get(0).date());
    }
}
