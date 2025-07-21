package com.devbrito.luizalabs.ordersadapter.repository;

import com.devbrito.luizalabs.ordersadapter.domain.document.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Integer> {
}
