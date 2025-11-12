package com.food.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDetailResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String phone;
    private String address;
}
