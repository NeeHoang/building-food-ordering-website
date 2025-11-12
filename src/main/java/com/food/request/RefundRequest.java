package com.food.request;

import lombok.Data;

@Data
public class RefundRequest {
    private String txnRef;
    private String orderInfo;
    private long amount;
    private String reason;
}
