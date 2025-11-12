package com.food.response;

import com.food.model.entities.OrderItem;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private Long id;
    private Long orderId;
    private int quantity;
    private Long productId;
    private Double price;
    private Double totalPrice;
    public static OrderDetailResponse from(OrderItem orderItem) {
        OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .productId(orderItem.getProduct().getId())
                .totalPrice(orderItem.getTotalPrice())
                .build();

        return orderDetailResponse;
    }
}
