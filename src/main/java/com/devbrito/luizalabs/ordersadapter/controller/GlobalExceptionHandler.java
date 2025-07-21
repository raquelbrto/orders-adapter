package com.devbrito.luizalabs.ordersadapter.controller;

import com.devbrito.luizalabs.ordersadapter.exceptions.FileEmptyException;
import com.devbrito.luizalabs.ordersadapter.exceptions.FileNotCompatibleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Map<String, String>> handleMultipartException(MultipartException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Erro no upload do arquivo");
        error.put("message", "Certifique-se de que está enviando um arquivo válido no formato multipart/form-data com o Content-Type correto");
        error.put("details", "A requisição deve ter Content-Type: multipart/form-data; boundary=...");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(FileEmptyException.class)
    public ResponseEntity<Map<String, String>> handleFileEmptyException(FileEmptyException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Arquivo vazio");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(FileNotCompatibleException.class)
    public ResponseEntity<Map<String, String>> handleFileNotCompatibleException(FileNotCompatibleException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Arquivo incompatível");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
//        Map<String, String> error = new HashMap<>();
//        error.put("error", "Erro interno do servidor");
//        error.put("message", "Ocorreu um erro inesperado. Tente novamente.");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
//    }
}