package com.food.model.entities;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="vouchers")
public class Voucher extends BaseEntity {

    @Column(name="code")
    private String code;

    @Column(name="discount_percent")
    private Double discountPercent;

    @Column(name="valid_from")
    private LocalDateTime validFrom;

    @Column(name="valid_to")
    private LocalDateTime validTo;

}
