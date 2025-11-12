package com.food.repositories;
import java.util.List;

import com.food.model.entities.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {

    //Lấy các bảng giá không hoạt động
    List<ProductInventory> findByProductId(Long productId);

    //Tìm bảng giá đang hoạt động
    @Query("SELECT pi FROM ProductInventory pi WHERE pi.product.id = :productId AND pi.isCurrent = true")
    ProductInventory findCurrentByProductId(@Param("productId") Long productId);

}
