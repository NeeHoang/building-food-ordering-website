package com.food.services.impl;

import com.food.model.context.CartContext;
import com.food.dto.CartItemDTO;
import com.food.dto.response.CartResponse;
import com.food.model.entities.Cart;
import com.food.model.entities.CartItem;
import com.food.model.entities.User;
import com.food.repositories.CartItemRepository;
import com.food.repositories.CartRepository;
import com.food.repositories.UserRepository;
import com.food.services.ICartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartResponse getOrCreateCart(CartContext context) {
        Cart cart = findOrCreateCart(context);

        List<CartItemDTO> items = cartItemRepository.findByCart(cart)
                .stream()
                .map(item -> CartItemDTO.builder()
                        .cartItemId(item.getId())
                        .productId(item.getProduct().getId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build())
                .collect(Collectors.toList());

        return new CartResponse(cart.getId(), items);
    }

    @Override
    public void clearCart(CartContext context) {
        Cart cart = findOrCreateCart(context);
        cartItemRepository.deleteAllByCartId(cart.getId());
        log.info("Cleared cart: {}", context.getIdentifier());
    }

    @Override
    public CartResponse mergeGuestCartToUserCart(CartContext guestContext, Long userId) {
        Optional<Cart> guestCartOpt = guestContext.getSessionId()
                .flatMap(cartRepository::findBySessionId);

        Optional<Cart> userCartOpt = cartRepository.findByUserId(userId);

        if (guestCartOpt.isEmpty()) {
            return getOrCreateCart(new CartContext(userId, null));
        }

        Cart guestCart = guestCartOpt.get();

        if (userCartOpt.isPresent()) {
            Cart userCart = userCartOpt.get();

            for (CartItem item : guestCart.getItems()) {
                Optional<CartItem> existing = userCart.getItems().stream()
                        .filter(i -> i.getProduct().getId().equals(item.getProduct().getId()))
                        .findFirst();

                if (existing.isPresent()) {
                    existing.get().setQuantity(existing.get().getQuantity() + item.getQuantity());
                } else {
                    item.setCart(userCart);
                    userCart.getItems().add(item);
                }
            }

            cartRepository.save(userCart);
            cartRepository.delete(guestCart);

            return getOrCreateCart(new CartContext(userId, null));
        } else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            guestCart.setUser(user);
            guestCart.setSessionId(null);
            cartRepository.save(guestCart);

            return getOrCreateCart(new CartContext(userId, null));
        }
    }

    @Override
    public Cart createCart(CartContext context) {
        Cart.CartBuilder builder = Cart.builder();

        context.getUserId().ifPresent(userId -> {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
            builder.user(user);
        });

        context.getSessionId().ifPresent(builder::sessionId);

        return cartRepository.save(builder.build());
    }

    @Override
    public void deleteCart(Long userId) {
        cartRepository.findByUserId(userId).ifPresent(cartRepository::delete);
    }

    @Override
    public Cart findOrCreateCart(CartContext context) {
        return context.getUserId()
                .flatMap(cartRepository::findByUserId)
                .or(() -> context.getSessionId().flatMap(cartRepository::findBySessionId))
                .orElseGet(() -> createCart(context));
    }
}
