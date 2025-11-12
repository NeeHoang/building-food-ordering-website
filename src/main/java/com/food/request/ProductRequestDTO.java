package com.food.request;
import java.util.List;

import com.food.commons.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
        @NotBlank(message = "Product name required")
        private String name;

        private String description;

        @Size(max = 500, message = "Image URL must be less than 500 characters")
        private String image_url;

        @NotNull(message = "Category ID is required")
        private Long categoryId;

        private List<String> type;

        private Double price;

        private Long quantity;

        private String status;


}
