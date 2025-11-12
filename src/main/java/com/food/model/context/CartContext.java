package com.food.model.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class CartContext {

    private Long userId;
    private String sessionId; // dùng String thay vì UUID

    public Optional<Long> getUserId() {
        return Optional.ofNullable(userId);
    }

    public Optional<String> getSessionId() {
        return Optional.ofNullable(sessionId);
    }

    public String getIdentifier() {
        return userId != null ? "user-" + userId : "guest-" + sessionId;
    }
}
