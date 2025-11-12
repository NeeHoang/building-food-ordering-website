package com.food.controllers;

import java.util.List;

import com.food.model.entities.ProductInventory;
import com.food.request.ProductInventoryRequestDTO;
import com.food.response.ProductInventoryResponse;
import com.food.services.impl.ProductInventoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product_inventory")
@RequiredArgsConstructor
public class ProductInventoryController {
    private final ProductInventoryService productInventoryService;
    @GetMapping("/product/{product_id}")
    public ResponseEntity<?> getProductInventories(@PathVariable("product_id") Long productId) {
        try{
            List<ProductInventoryResponse> productInventories = productInventoryService.getProductInventories(productId);
            return ResponseEntity.ok(productInventories);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/product/{product_id}/inventory/{product_inventory_id}")
    public ResponseEntity<?> activeProductInventory(@PathVariable("product_id") Long productId, @PathVariable("product_inventory_id") Long productInventoryId) {
        try{
            productInventoryService.changeProductInventory(productId, productInventoryId);
            return ResponseEntity.ok("Cập nhật thành công");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/product/{product_id}/inventory/{id}")
    public ResponseEntity<?> deleteProductInventory(@PathVariable("product_id") Long productId, @PathVariable("id") Long id) {
        try{
            productInventoryService.deleteProductInventory(id);
            return ResponseEntity.ok("Xóa thành công");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/product/{product_id}/inventory")
    public ResponseEntity<?> addProductInventory(@RequestBody ProductInventoryRequestDTO productInventory,@PathVariable("product_id") Long productId) {
        try{
            ProductInventoryResponse productIn = productInventoryService.createProductInventory(productInventory,productId);
            return ResponseEntity.ok(productIn);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
