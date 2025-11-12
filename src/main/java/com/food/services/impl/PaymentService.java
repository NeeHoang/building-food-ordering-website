package com.food.services.impl;

import com.food.model.entities.Order;
import com.food.model.entities.Payment;
import com.food.repositories.OrderRepository;
import com.food.repositories.PaymentRepository;
import com.food.services.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public Payment createPaymentForOrder(Long orderId, String method) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));

        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(method)
                .paymentStatus("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment); // trả về payment.id dùng làm TxnRef
    }

    public void updatePaymentStatus(Long paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setPaymentStatus(status);
        paymentRepository.save(payment);

        Order order = payment.getOrder();
        if (order != null) {
            String orderStatus = switch (status.toUpperCase()) {
                case "COMPLETED" -> "confirmed";
                case "FAILED" -> "canceled";
                case "PENDING" -> "pending";
                default -> order.getStatus(); // giữ nguyên nếu status không hợp lệ
            };
            orderService.updateOrderStatus(order.getId(), orderStatus);
        }
    }
}

