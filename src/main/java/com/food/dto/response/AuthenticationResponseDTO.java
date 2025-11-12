package com.food.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponseDTO {
    private String token;
    private String email;
    private String role;
    private long expiresIn; // Thời gian hết hạn của token (giây)

}
