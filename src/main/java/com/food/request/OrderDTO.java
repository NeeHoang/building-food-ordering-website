package com.food.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @Min(value = 1,message = "User id must be > 0")
    private Long userId;

    private String fullName;

    private String address;

    @NotBlank(message = "Phone number is required")
    @Size(message = "Phone number must be at least 5 characters")
    private String phoneNumber;

    private String note;

    @Min(value = 0,message = "Total price must be > 0=")
    private Double totalPrice;

    private Long voucherId;

}
