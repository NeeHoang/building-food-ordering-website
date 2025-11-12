package com.food.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart")
public class Cart extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @Column(name = "session_id", nullable = true, unique = true)
    private String sessionId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();
}

