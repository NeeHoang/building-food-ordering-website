package com.food.exception;

import com.food.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleResponseStatusException(ResponseStatusException ex) {
        String message = ex.getReason();
        HttpStatus status = (HttpStatus) ex.getStatusCode();

        ErrorCode matchedCode = ErrorCode.UNCATEGORIZED_EXCEPTION;

        for (ErrorCode code : ErrorCode.values()) {
            if (code.getMessage().equalsIgnoreCase(message)) {
                matchedCode = code;
                break;
            }
        }

        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(matchedCode.getCode(), matchedCode.getMessage(), status.value()));
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleValidationException(MethodArgumentNotValidException ex) {
        List<Map<String, Object>> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "rejectedValue", error.getRejectedValue(),
                        "message", error.getDefaultMessage()
                ))
                .toList();

        Map<String, Object> extraData = new HashMap<>();
        extraData.put("errors", validationErrors);

        return ResponseEntity.badRequest().body(
                ApiResponse.error(1000, "Validation failed", 400, extraData)
        );
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleGenericException(Exception ex) {
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage(), errorCode.getStatusCode().value()));
    }
}