package com.food.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductDetailResponse {

    private String name;

    private String description;

    private Double price;

    private String imageUrl;

    private Long categoryId;

    private Long quantity;

    private String status;

    private String Type;
}
