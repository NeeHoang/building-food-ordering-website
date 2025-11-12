package com.food.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String phone;
    private String newPassword;
}
