package com.food.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInventoryResponse {
    private Long productId;
    private Long quantity;
    private Double price;
    private String status;
    private Boolean isCurrent;
    private LocalDateTime startAt;
}
