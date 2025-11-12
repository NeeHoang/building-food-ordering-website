package com.food.controllers;

import com.food.dto.response.CartResponse;
import com.food.model.context.CartContext;
import com.food.services.JwtService;
import com.food.services.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;
    private final JwtService jwtService;

    private CartContext extractCartContext(String bearerToken, String sessionId) {
        Optional<Long> userIdOpt = Optional.empty();
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            try {
                String token = bearerToken.substring(7); // remove "Bearer "
                String userIdStr = jwtService.extractUserId(token);
                userIdOpt = Optional.of(Long.parseLong(userIdStr));
            } catch (Exception e) {
            }
        }

        return new CartContext(userIdOpt.orElse(null), sessionId);
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestHeader(value = "Session-Id", required = false) String sessionId
    ) {
        if (sessionId == null) {
            sessionId = UUID.randomUUID().toString(); // dùng String luôn
        }

        CartContext context = extractCartContext(bearerToken, sessionId);
        CartResponse cartResponse = cartService.getOrCreateCart(context);

        return ResponseEntity.ok()
                .header("Set-Session-Id", sessionId)
                .body(cartResponse);
    }


    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(
            @RequestHeader(value = "Authorization", required = false) String bearerToken,
            @RequestHeader(value = "Session-Id", required = false) String sessionId
    ) {
        CartContext context = extractCartContext(bearerToken, sessionId);
        cartService.clearCart(context);
        return ResponseEntity.ok("Cart cleared");
    }

    @PostMapping("/merge")
    public ResponseEntity<CartResponse> mergeGuestToUser(
            @RequestHeader("Session-Id") String sessionId,
            @RequestHeader("Authorization") String bearerToken
    ) {
        if (!bearerToken.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(null);
        }
        String token = bearerToken.replace("Bearer ", "");
        Long userId = Long.parseLong(jwtService.extractUserId(token));

        CartContext guestContext = new CartContext(null, sessionId);
        CartResponse merged = cartService.mergeGuestCartToUserCart(guestContext, userId);
        return ResponseEntity.ok(merged);
    }
}
