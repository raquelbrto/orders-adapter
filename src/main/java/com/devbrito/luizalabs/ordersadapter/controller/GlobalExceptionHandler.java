package com.devbrito.luizalabs.ordersadapter.controller;

import com.devbrito.luizalabs.ordersadapter.domain.dto.ApiResponseErrorDTO;
import com.devbrito.luizalabs.ordersadapter.exceptions.FileEmptyException;
import com.devbrito.luizalabs.ordersadapter.exceptions.FileNotCompatibleException;
import com.devbrito.luizalabs.ordersadapter.exceptions.InvalidParamDateException;
import com.devbrito.luizalabs.ordersadapter.exceptions.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    private ResponseEntity<ApiResponseErrorDTO> orderNotFoundHandler(OrderNotFoundException exception) {
        ApiResponseErrorDTO responseError = new ApiResponseErrorDTO(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
    }

    @ExceptionHandler(InvalidParamDateException.class)
    private ResponseEntity<ApiResponseErrorDTO> invalidParamDateHandler(InvalidParamDateException exception) {
        ApiResponseErrorDTO responseError = new ApiResponseErrorDTO(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ApiResponseErrorDTO> handleMultipartException(MultipartException ex) {
        ApiResponseErrorDTO responseError = new ApiResponseErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(FileEmptyException.class)
    public ResponseEntity<ApiResponseErrorDTO> handleFileEmptyException(FileEmptyException ex) {
        ApiResponseErrorDTO responseError = new ApiResponseErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(FileNotCompatibleException.class)
    public ResponseEntity<ApiResponseErrorDTO> handleFileNotCompatibleException(FileNotCompatibleException ex) {
        ApiResponseErrorDTO responseError = new ApiResponseErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseErrorDTO> handleGenericException(Exception ex) {
        ApiResponseErrorDTO responseError = new ApiResponseErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
    }
}