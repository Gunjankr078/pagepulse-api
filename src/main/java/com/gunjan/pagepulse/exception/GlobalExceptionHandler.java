package com.gunjan.pagepulse.exception;

import com.gunjan.pagepulse.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .getFirst()
                .getDefaultMessage();

        ErrorResponse response = ErrorResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .message(message)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(WebsiteUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleWebsiteUnavailable(
            WebsiteUnavailableException ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .error("Service Unavailable")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(InvalidWebsiteException.class)
    public ResponseEntity<ErrorResponse> handleInvalidWebsite(
            InvalidWebsiteException ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(WebsiteAuditException.class)
    public ResponseEntity<ErrorResponse> handleWebsiteAudit(
            WebsiteAuditException ex,
            HttpServletRequest request) {

        ErrorResponse response = ErrorResponse.builder()
                .requestId(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}