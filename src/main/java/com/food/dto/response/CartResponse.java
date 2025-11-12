package com.food.dto.response;

import com.food.dto.CartItemDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    private Long cartId;
    private List<CartItemDTO> items;
}
