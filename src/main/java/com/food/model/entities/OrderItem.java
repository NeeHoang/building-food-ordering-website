package com.food.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name="quantity")
    private int quantity;

    @Column(name="price")
    private Double price;

    @Column(name="total_price")
    private Double totalPrice;
}
