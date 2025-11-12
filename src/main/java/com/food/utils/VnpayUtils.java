package com.food.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

public class VnpayUtils {

    public static String hmacSHA512(String key, String data) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA512");
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(secretKey);
            byte[] bytes = mac.doFinal(data.getBytes());
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo HMAC SHA512", e);
        }
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        return (ip != null && !ip.isEmpty()) ? ip : request.getRemoteAddr();
    }

    public static String getRandomNumber(int length) {
        String chars = "0123456789";
        StringBuilder result = new StringBuilder();
        Random rnd = new Random();
        while (result.length() < length) {
            result.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return result.toString();
    }

    public static Map<String, String> getFieldsFromRequest(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();
        request.getParameterMap().forEach((key, values) -> fields.put(key, values[0]));
        return fields;
    }
}

