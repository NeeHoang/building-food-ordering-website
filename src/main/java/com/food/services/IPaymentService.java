package com.food.services;

import com.food.model.entities.Payment;

public interface IPaymentService {

    Payment createPaymentForOrder(Long orderId, String method);

    void updatePaymentStatus(Long paymentId, String status);
}
