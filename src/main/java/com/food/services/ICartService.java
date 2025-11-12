package com.food.services;

import com.food.dto.response.CartResponse;
import com.food.model.context.CartContext;
import com.food.model.entities.Cart;

public interface ICartService {
    CartResponse getOrCreateCart(CartContext context);

    void clearCart(CartContext context);

    CartResponse mergeGuestCartToUserCart(CartContext guestContext, Long userId);

    Cart createCart(CartContext context);

    void deleteCart(Long userId);

    Cart findOrCreateCart(CartContext context);

}
