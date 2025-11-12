package com.food.response;
import com.food.model.entities.BaseEntity;
import com.food.model.entities.ProductInventory;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image_url;
    private Long quantity;
    private String category_name;
    private String type;
    private String status;
}
