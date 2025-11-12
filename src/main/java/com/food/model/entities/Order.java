package com.food.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity{

    @Column(name="status")
    private String status;

    @Column(name="full_name")
    private String fullName;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="address")
    private String address;

    @Column(name="note")
    private String note;

    @Column(name="total_price")
    private Double totalPrice;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.PERSIST})
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Payment> payments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="voucher_id")
    private Voucher voucher;

    @Column(name="active")
    private Boolean active;
}
