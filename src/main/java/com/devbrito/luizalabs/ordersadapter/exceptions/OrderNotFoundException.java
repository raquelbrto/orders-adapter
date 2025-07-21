package com.devbrito.luizalabs.ordersadapter.exceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Integer id) {
        super("Order not found with id: " + id);
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}