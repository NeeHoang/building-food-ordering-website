package com.food.response;

import com.food.model.entities.CartItem;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {
    private Long id;
    private Long cartId;
    private Long productId;
    private int quantity;
    private Double price;

    private String productName;
    private String productImage;

    public static CartItemResponse from(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .cartId(cartItem.getCart().getId())
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .productName(cartItem.getProduct().getName())
                .productImage(cartItem.getProduct().getImageUrl())
                .build();
    }
}
