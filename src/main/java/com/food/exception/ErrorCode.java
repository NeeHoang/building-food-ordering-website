package com.food.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(1006, "Email hoặc mật khẩu không chính xác", HttpStatus.UNAUTHORIZED),
    INVALID_EMAIL(1009, "Email không tồn tại", HttpStatus.UNAUTHORIZED),
    WRONG_PASSWORD(1011, "Mật khẩu sai", HttpStatus.UNAUTHORIZED),
    EMAIL_ALREADY_EXISTS(1012, "Email đã tồn tại", HttpStatus.BAD_REQUEST),
    FIELD_REQUIRED(1013, "Trường %s là bắt buộc", HttpStatus.BAD_REQUEST),  // Thêm để xử lý lỗi field
    ROLE_NOT_FOUND(1003, "Default role not found", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_OR_EXPIRED_TOKEN(40101, "Invalid or expired token", HttpStatus.UNAUTHORIZED),
    TOKEN_PROCESSING_FAILED(50001, "Could not process token", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND(1004, "%s không tồn tại", HttpStatus.NOT_FOUND),
    PRODUCT_ALREADY_EXIST(1012, "Product already exist", HttpStatus.BAD_REQUEST);


    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getFormattedMessage(Object... args) {
        return String.format(this.message, args);
    }
}
