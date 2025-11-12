package com.food.services.impl;

import com.food.customexceptions.DataNotFoundException;
import com.food.model.entities.Product;
import com.food.model.entities.ProductInventory;
import com.food.repositories.ProductInventoryRepository;
import com.food.repositories.ProductRepository;
import com.food.request.ProductInventoryRequestDTO;
import com.food.response.ProductInventoryResponse;
import com.food.services.IProductInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInventoryService implements IProductInventoryService {
    private final ProductInventoryRepository productInventoryRepository;
    private final ProductRepository productRepository;


    @Override
    public List<ProductInventoryResponse> getProductInventories(Long productId) {
        List<ProductInventory> productInvs = productInventoryRepository.findByProductId(productId);
        List<ProductInventoryResponse> result = new ArrayList<>();
        for (ProductInventory item : productInvs) {
            ProductInventoryResponse productResponse = new ProductInventoryResponse();
            productResponse.setProductId(item.getId());
            productResponse.setPrice(item.getPrice());
            productResponse.setQuantity(item.getQuantity());
            productResponse.setIsCurrent(item.getIsCurrent());
            result.add(productResponse);
        }
        return result;
    }

    @Override
    public ProductInventoryResponse createProductInventory(ProductInventoryRequestDTO productInventory, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product not found"));
        //Nếu mà muốn dùng luôn bảng giá mới thì ta phải set bảng giá đang hoạt động hiện tại
        // is_current = true thành false
        if(productInventory.getIsCurrent() == true){
            changeProductInventory(productId);
        }
        ProductInventory productInv = ProductInventory.builder()
                .product(product)
                .price(productInventory.getPrice())
                .quantity(productInventory.getQuantity())
                .isCurrent(productInventory.getIsCurrent())
                .status(productInventory.getStatus())
                .startDate(LocalDateTime.now())
                .build();
        productInventoryRepository.save(productInv);
        ProductInventoryResponse productResponse = ProductInventoryResponse.builder()
                .isCurrent(productInv.getIsCurrent())
                .status(productInv.getStatus())
                .productId(productInv.getId())
                .price(productInv.getPrice())
                .quantity(productInv.getQuantity())
                .startAt(productInv.getStartDate())
                .build();
        return productResponse;
    }

    //Thay đổi giá (Logic ta tìm bảng giá hiện tại đang hoạt động rồi tắt nó đi(set = false), xong đó cập nhật bảng giá mới)
    @Override
    public void changeProductInventory(Long productId, Long productInventoryId) {
        ProductInventory existProductInv = productInventoryRepository.findCurrentByProductId(productId);
        if (existProductInv != null) {
            existProductInv.setIsCurrent(false);
            existProductInv.setEndDate(LocalDateTime.now());
            productInventoryRepository.save(existProductInv);
        }
        List<ProductInventory> productInvs = productInventoryRepository.findByProductId(productId);
        for (ProductInventory item : productInvs) {
            if(item.getId() == productInventoryId){
                item.setIsCurrent(true); //Cập nhật bảng giá mới
                item.setStartDate(LocalDateTime.now());
                item.setEndDate(null);
                productInventoryRepository.save(item);
            }
        }
    }

    @Override
    public void deleteProductInventory(Long productInventoryId) {
        productInventoryRepository.deleteById(productInventoryId);
    }
    private void changeProductInventory(Long productId) {
        ProductInventory existProductInv = productInventoryRepository.findCurrentByProductId(productId);
        if (existProductInv != null) {
            existProductInv.setIsCurrent(false);
            existProductInv.setEndDate(LocalDateTime.now());
            productInventoryRepository.save(existProductInv);
        }
    }

}
