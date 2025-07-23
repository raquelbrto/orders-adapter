package com.devbrito.luizalabs.ordersadapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class OrdersAdapterApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdersAdapterApplication.class, args);
    }

}
