package com.food.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;
    private Map<String, Object> error;

    public static <T> ApiResponse<T> delete(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus("success");
        response.setMessage(message);
        return response;
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus("success");
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    public static ApiResponse<Map<String, Object>> error(int code, String message, int statusCode) {
        ApiResponse<Map<String, Object>> response = new ApiResponse<>();
        response.setStatus("error");
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("code", code);
        errorDetails.put("message", message);
        errorDetails.put("status", statusCode);
        response.setError(errorDetails);
        return response;
    }

    public static ApiResponse<Map<String, Object>> error(int code, String message, int statusCode, Map<String, Object> extraData) {
        ApiResponse<Map<String, Object>> response = new ApiResponse<>();
        response.setStatus("error");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("code", code);
        errorDetails.put("message", message);
        errorDetails.put("status", statusCode);
        errorDetails.putAll(extraData);

        response.setError(errorDetails);
        return response;
    }


}