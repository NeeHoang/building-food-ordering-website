package com.food.response;

import java.util.List;
import com.food.model.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ProductListResponse {
    private List<ProductResponseDTO> products;
    private int totalPages;
}
