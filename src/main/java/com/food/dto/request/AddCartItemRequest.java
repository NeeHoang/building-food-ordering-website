package com.food.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCartItemRequest {
    private Long productId;
    private Integer quantity;
    private BigDecimal totalPrice;
}

