package com.food.controllers;

import com.food.customexceptions.DataNotFoundException;
import com.food.dto.CartItemDTO;
import com.food.dto.request.AddCartItemRequest;
import com.food.model.context.CartContext;
import com.food.model.entities.CartItem;
import com.food.response.CartItemResponse;
import com.food.services.JwtService;
import com.food.services.ICartItemService;
import com.food.services.ICartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cart/items")
@RequiredArgsConstructor
@Slf4j
public class CartItemController {

    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final JwtService jwtService;

    private CartContext extractCartContext(String bearerToken, String sessionId) {
        Long userId = null;
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            try {
                String token = bearerToken.substring(7);
                userId = Long.parseLong(jwtService.extractUserId(token));
            } catch (Exception e) {
                log.warn("JWT validation failed: {}", e.getMessage());
            }
        }
        return new CartContext(userId, sessionId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCartItem(
            @RequestBody AddCartItemRequest request,
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestHeader(value = "Session-Id", required = false) String sessionId
    ) {
        CartContext context = extractCartContext(bearerToken, sessionId);
        Long cartId = cartService.findOrCreateCart(context).getId();

        try {
            CartItem item = cartItemService.saveCartItem(cartId, request);
            CartItemResponse response = CartItemResponse.from(item);

            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.ok(Map.of(
                    "message", e.getMessage()
            ));
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(
            @PathVariable("id") Long id,
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestHeader(value = "Session-Id", required = false) String sessionId
    ) {
        CartContext context = extractCartContext(bearerToken, sessionId);
        try {
            cartItemService.deleteCartItem(id, context);
            return ResponseEntity.ok("Xóa sản phẩm khỏi giỏ hàng thành công");
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi hệ thống"));
        }
    }

    @PatchMapping("/{id}/increase")
    public ResponseEntity<?> increaseQuantity(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestHeader(value = "Session-Id", required = false) String sessionId
    ) {
        CartContext context = extractCartContext(bearerToken, sessionId);
        try {
            cartItemService.increaseQuantity(id, context);
            return ResponseEntity.ok(Map.of("message", "Quantity increased"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi hệ thống"));
        }
    }

    @PatchMapping("/{id}/decrease")
    public ResponseEntity<?> decreaseQuantity(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestHeader(value = "Session-Id", required = false) String sessionId
    ) {
        CartContext context = extractCartContext(bearerToken, sessionId);
        try {
            cartItemService.decreaseQuantity(id, context);
            return ResponseEntity.ok(Map.of("message", "Quantity decreased"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi hệ thống"));
        }
    }

    @GetMapping
    public ResponseEntity<?> getCartItems(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestHeader(value = "Session-Id", required = false) String sessionId
    ) {
        try {
            CartContext context = extractCartContext(bearerToken, sessionId);
            List<CartItem> cartItems = cartItemService.getCartItems(context);

            List<CartItemDTO> response = cartItems.stream()
                    .map(item -> CartItemDTO.builder()
                            .cartItemId(item.getId())
                            .productId(item.getProduct().getId())
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .build())
                    .toList();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
