package com.food.services.impl;

import com.food.commons.ProductType;
import com.food.customexceptions.DataNotFoundException;
import com.food.exception.ErrorCode;
import com.food.model.entities.Category;
import com.food.model.entities.Product;
import com.food.model.entities.ProductInventory;
import com.food.request.ProductPresentRequestDTO;
import com.food.request.ProductRequestDTO;
import com.food.repositories.ProductInventoryRepository;
import com.food.response.ProductPresentResponse;
import com.food.response.ProductResponseDTO;
import com.food.repositories.CategoriesRepository;
import com.food.repositories.ProductRepository;
import com.food.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductInventoryRepository productInventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductResponseDTO> getAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> listProduct = new ArrayList<>();
        for(Product item : products){
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            ProductInventory productInventory = productInventoryRepository.findCurrentByProductId(item.getId());
            productResponseDTO.setId(item.getId());
            productResponseDTO.setName(item.getName());
            productResponseDTO.setCategory_name(item.getCategory().getName());
            productResponseDTO.setType(item.getType());
            productResponseDTO.setDescription(item.getDescription());
            productResponseDTO.setPrice(productInventory.getPrice());
            productResponseDTO.setQuantity(productInventory.getQuantity());
            productResponseDTO.setStatus(productInventory.getStatus());
            listProduct.add(productResponseDTO);
        }
        return listProduct;
    }

    @Override
    public Page<ProductResponseDTO> getAllProduct(PageRequest pageRequest) {
//        return productRepository.findAll(pageRequest).map(ProductResponseDTO::fromProduct);
        return null;
    }

    @Override
    public Product getProductById(Long id) {
//        return productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found with id=" + id));
        return null;
    }

    @Override
    public Long saveProduct(ProductRequestDTO body) {
        Category category = categoriesRepository.findById(body.getCategoryId())
                                                            .orElseThrow(() -> new DataNotFoundException("Category not found"));

        if (productRepository.existsByName(body.getName())) {
            throw new ResponseStatusException(
                    ErrorCode.PRODUCT_ALREADY_EXIST.getStatusCode(),
                    ErrorCode.PRODUCT_ALREADY_EXIST.getMessage()
            );
        }

        List<ProductType> typeEnums = body.getType().stream()
                .map(String::toUpperCase)
                .map(ProductType::valueOf)
                .toList();

        Product product = new Product();

        product.setName(body.getName());
        product.setDescription(body.getDescription());
        product.setCategory(category);
        product.setImageUrl(body.getImage_url());
        product.setType(String.join(",", body.getType()));

        productRepository.save(product);

        ProductInventory productInventory = new ProductInventory();

        productInventory.setProduct(product);
        productInventory.setPrice(body.getPrice());
        productInventory.setQuantity(body.getQuantity());
        productInventory.setStatus(body.getStatus());
        productInventory.setIsCurrent(true);
        productInventory.setStartDate(LocalDateTime.now());

        productInventoryRepository.save(productInventory);

        return product.getId();
    }

    @Override
    public ProductPresentResponse updateProduct(ProductPresentRequestDTO body, Long id) {
//        Product existingProduct = getProductById(id);
//        if(existingProduct != null) {
//            Category existingCategory = categoriesRepository.findById(body.getCategory_id()).orElseThrow(() ->
//                    new DataNotFoundException("Category not found with id=" + body.getCategory_id()));
//            existingProduct.setName(body.getName());
//            existingProduct.setDescription(body.getDescription());
//            existingProduct.setImageUrl(body.getImage_url());
//            existingProduct.setType(body.getType().stream().collect(Collectors.joining(",")));
//        return productRepository.save(existingProduct);
//        }
//        return null;

        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found"));
        String type = String.join(",", body.getType());
        Category category = categoriesRepository.findById(body.getCategoryId()).orElseThrow();

        existingProduct.setType(type);
        existingProduct.setCategory(category);
        existingProduct.setName(body.getName());
        existingProduct.setDescription(body.getDescription());
        existingProduct.setImageUrl(body.getImageUrl());

        productRepository.save(existingProduct);

        ProductPresentResponse response = new ProductPresentResponse();
        response.setType(type);
        response.setCategoryName(existingProduct.getCategory().getName());
        response.setName(body.getName());
        response.setDescription(body.getDescription());
        response.setImageUrl(body.getImageUrl());
        response.setUpdatedAt(LocalDateTime.now());

        return response;
    }

    //Xóa mềm(bằng cách set bảng giá đang hoạt động = false
    @Override
    public void deleteProduct(Long id) {
        ProductInventory productInventory = productInventoryRepository.findCurrentByProductId(id);
        productInventory.setIsCurrent(false);
    }

}
