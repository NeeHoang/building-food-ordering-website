package com.food.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long cartItemId;
    private Long productId;

    // Không cần truyền quantity khi thêm vào giỏ hàng (mặc định là 1)
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity = 1;

    private double price;
}
