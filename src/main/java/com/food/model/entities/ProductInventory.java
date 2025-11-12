package com.food.model.entities;

import com.food.commons.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name="product_inventory")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventory {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name="price")
    private Double price;

    @Column(name="quantity")
    private Long quantity;

    @Column(name="status")
    private String status;

    @Column(name="is_current")
    private Boolean isCurrent;

    @Column(name="start_date")
    private LocalDateTime startDate;

    @Column(name="end_date")
    private LocalDateTime endDate;
}

