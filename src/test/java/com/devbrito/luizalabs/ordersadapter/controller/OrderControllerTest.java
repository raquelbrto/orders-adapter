package com.devbrito.luizalabs.ordersadapter.controller;

import com.devbrito.luizalabs.ordersadapter.domain.dto.OrderResponseDTO;
import com.devbrito.luizalabs.ordersadapter.domain.dto.UserOrdersResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void cleanDatabase() {
        mongoTemplate.getDb().drop();
    }

    @Test
    void testSaveOrders() throws Exception {
        Path path = Path.of("src/test/resources/orders.txt");
        MockMultipartFile file = new MockMultipartFile("file", "orders.txt", "text/plain", Files.readAllBytes(path));

        MvcResult result = mockMvc.perform(multipart("/api/v1/orders").file(file))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        UserOrdersResponseDTO[] dtos = objectMapper.readValue(response, UserOrdersResponseDTO[].class);
        assertThat(dtos).isNotEmpty();
    }

    @Test
    void testFindById() throws Exception {
        Path path = Path.of("src/test/resources/orders.txt");
        MockMultipartFile file = new MockMultipartFile("file", "orders.txt", "text/plain", Files.readAllBytes(path));
        mockMvc.perform(multipart("/api/v1/orders").file(file)).andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/orders/12345"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testListOrdersWithDateFilter() throws Exception {
        Path path = Path.of("src/test/resources/orders.txt");
        MockMultipartFile file = new MockMultipartFile("file", "orders.txt", "text/plain", Files.readAllBytes(path));
        mockMvc.perform(multipart("/api/v1/orders").file(file)).andExpect(status().isOk());

        String startDate = LocalDate.of(2020, 12, 1).toString();
        String endDate = LocalDate.of(2021, 2, 3).toString();

        MvcResult result = mockMvc.perform(get("/api/v1/orders")
                        .param("start_date", startDate)
                        .param("end_date", endDate))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        OrderResponseDTO[] dtos = objectMapper.readValue(response, OrderResponseDTO[].class);
        assertThat(dtos)
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void testListOrders() throws Exception {
        Path path = Path.of("src/test/resources/orders.txt");
        MockMultipartFile file = new MockMultipartFile("file", "orders.txt", "text/plain", Files.readAllBytes(path));
        mockMvc.perform(multipart("/api/v1/orders").file(file)).andExpect(status().isOk());

        MvcResult result = mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        OrderResponseDTO[] dtos = objectMapper.readValue(response, OrderResponseDTO[].class);
        assertThat(dtos).hasSize(2);
    }
} 