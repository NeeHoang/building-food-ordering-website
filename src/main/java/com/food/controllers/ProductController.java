package com.food.controllers;

import java.util.List;

import com.food.dto.response.ApiResponse;
import com.food.model.context.UserContext;
import com.food.request.ProductPresentRequestDTO;
import com.food.request.ProductRequestDTO;
import com.food.response.ProductPresentResponse;
import com.food.response.ProductResponseDTO;
import com.food.services.JwtService;
import com.food.services.impl.FileStorageService;
import com.food.services.impl.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final FileStorageService fileStorageService;
    private final JwtService jwtService;

    private UserContext extractUserContext(String bearerToken) {
        Long userId = null;
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            try {
                String token = bearerToken.substring(7); // remove "Bearer "
                String userIdStr = jwtService.extractUserId(token);
                userId = Long.parseLong(userIdStr);
            } catch (Exception e) {
                log.warn("Failed to extract userId from token", e);
            }
        }
        return new UserContext(userId);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getProducts(
            @RequestHeader("Authorization") String bearerToken) {


            List<ProductResponseDTO> products = productService.getAll();
            return ResponseEntity.ok(ApiResponse.success(products, "Get all products succesfull"));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Long>> addProduct(
            @RequestHeader("Authorization") String bearerToken,
            @Valid @RequestBody ProductRequestDTO productRequestDTO) {

        Long productId  = productService.saveProduct(productRequestDTO);
        return ResponseEntity.ok(ApiResponse.success(productId, "Add product successfull"));
    }

    @DeleteMapping("/{productId}") //Xóa mềm
    public ResponseEntity<ApiResponse<String>> deleteProduct(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable @Min(1) Long productId) {

        productService.deleteProduct(productId);
        return ResponseEntity.ok(ApiResponse.delete("Delete product succesfull"));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductPresentResponse>> updateProduct(
            @RequestHeader("Authorization") String bearerToken,
            @Valid @RequestBody ProductPresentRequestDTO productRequestDTO,
            @Min(1) @PathVariable Long productId) {

        ProductPresentResponse product = productService.updateProduct(productRequestDTO,productId);
        return ResponseEntity.ok(ApiResponse.success(product, "Update product successfull"));
    }
}