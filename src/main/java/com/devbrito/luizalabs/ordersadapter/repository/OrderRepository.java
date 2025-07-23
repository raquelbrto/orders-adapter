package com.devbrito.luizalabs.ordersadapter.repository;

import com.devbrito.luizalabs.ordersadapter.domain.document.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, Integer> {

    @Query("{ 'date': { $gte: ?0, $lte: ?1 } }")
    List<Order> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
