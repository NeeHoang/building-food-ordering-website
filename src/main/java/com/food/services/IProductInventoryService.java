package com.food.services;
import java.util.List;
import com.food.model.entities.ProductInventory;
import com.food.request.ProductInventoryRequestDTO;
import com.food.response.ProductInventoryResponse;
import org.springframework.stereotype.Service;

@Service
public interface IProductInventoryService {
    List<ProductInventoryResponse> getProductInventories(Long productId);
    ProductInventoryResponse createProductInventory(ProductInventoryRequestDTO productInventory, Long productId);
    void changeProductInventory(Long productId, Long productInventoryId);
    void deleteProductInventory(Long productInventoryId);
}
