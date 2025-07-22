package com.devbrito.luizalabs.ordersadapter.exceptions;

public class FileEmptyException extends RuntimeException {
    
    public FileEmptyException(String filename) {
        super("File is empty: " + filename);
    }
} 