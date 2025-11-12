package com.food.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class UserRequestDTO implements Serializable {

    @NotBlank(message = "Name must be not blank")
    private String name;

    @Email(message = "email invalid format")
    private String email;

    @NotNull(message = "password can not null")
    private String password;

    @NotBlank(message = "phone can not blank")
    private String phone;

    @NotEmpty(message = "address can not empty")
    private String address;
}
