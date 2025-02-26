package com.aurora5.aurora;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 400번대 (클라이언트 오류) 로깅
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<String> handleClientErrors(Exception ex, WebRequest request) {
        logger.error("Client Error (4xx): {} - {}", request.getDescription(false), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client error occurred: " + ex.getMessage());
    }

    // 500번대 (서버 오류) 로깅
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleServerErrors(Exception ex, WebRequest request) {
        logger.error("Server Error (5xx): {} - {}", request.getDescription(false), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred: " + ex.getMessage());
    }
}
