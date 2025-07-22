package com.devbrito.luizalabs.ordersadapter.exceptions;

public class FileNotCompatibleException extends RuntimeException {
    
    public FileNotCompatibleException(String filename, String expectedType) {
        super("File is not compatible: " + filename + ". Expected: " + expectedType);
    }
    
    public FileNotCompatibleException(String message) {
        super(message);
    }
} 