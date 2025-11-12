package com.food.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@Table(name="reviews")
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="rating")
    private int rating;

    @Column(name="comment")
    private String comment;

    @Column(name="created_at")
    private LocalDateTime createdAt;
}
