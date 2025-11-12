package com.food.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    private String name;

    private String description;

    private Double price;

    private String imageUrl;

    private Long categoryId;

    private Long quantity;

    private String status;

}
