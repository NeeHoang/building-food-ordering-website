package com.food.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ProductInventoryRequestDTO {
    @JsonProperty(value = "quantity")
    @Min(value = 0,message="quan must be > 0")
    private Long quantity;

    @JsonProperty("price")
    @Min(value = 0,message = "price must be >= 0")
    private Double price;


    @JsonProperty("status")
    private String status;

    @JsonProperty("is_current")
    private Boolean isCurrent;
}
