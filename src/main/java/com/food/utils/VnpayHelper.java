package com.food.utils;

import lombok.experimental.UtilityClass;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@UtilityClass
public class VnpayHelper {

    public String hashAllFields(Map<String, String> fields, String secretKey) throws Exception {
        List<String> sortedKeys = new ArrayList<>(fields.keySet());
        Collections.sort(sortedKeys);

        StringBuilder hashData = new StringBuilder();
        boolean first = true;
        for (String key : sortedKeys) {
            String value = fields.get(key);
            if (value != null && !value.isEmpty()) {
                if (!first) {
                    hashData.append('&');
                }
                hashData.append(URLEncoder.encode(key, StandardCharsets.US_ASCII.toString()))
                        .append('=')
                        .append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
                first = false;
            }
        }
        return hmacSHA512(secretKey, hashData.toString());
    }

    public String hmacSHA512(String key, String data) throws Exception {
        Mac hmac512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmac512.init(secretKey);
        byte[] bytes = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


}


