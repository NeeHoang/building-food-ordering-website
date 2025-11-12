package com.food.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDTO {
    @JsonProperty("order_id")
    @Min(value=1, message = "Order's ID must be > 0")
    private Long orderId;

    @Min(value=1, message = "Product's ID must be > 0")
    @JsonProperty("product_id")
    private Long productId;

    @Min(value=1, message = "number_of_products must be >= 1")
    private int quantity;

    @Min(value=0, message = "Price must be >= 0")
    private Double price;

    @Min(value=0, message = "total_price must be >= 0")
    @JsonProperty("total_price")
    private Double totalPrice;
}
