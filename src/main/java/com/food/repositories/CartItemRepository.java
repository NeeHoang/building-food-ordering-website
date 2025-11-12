package com.food.repositories;

import com.food.model.entities.Cart;
import com.food.model.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
    void deleteAllByCartId(Long cartId);

}
