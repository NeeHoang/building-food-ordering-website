package com.food.controllers;

import com.food.model.entities.Payment;
import com.food.request.RefundRequest;
import com.food.services.IPaymentService;
import com.food.services.impl.VnpayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vnpay")
@RequiredArgsConstructor
@Slf4j
public class VnpayController {

    private final IPaymentService paymentService;
    private final VnpayService vnPayService;

    @PostMapping("/create-payment")
    public ResponseEntity<String> createPayment(HttpServletRequest request,
                                                @RequestParam("orderId") Long orderId) {
        try {
            Payment payment = paymentService.createPaymentForOrder(orderId, "online");
            int amount = payment.getOrder().getTotalPrice().intValue();

            String paymentUrl = vnPayService.createPaymentUrl(request, amount, payment.getId()); // truyền paymentId

            return ResponseEntity.ok(paymentUrl);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/payment-return")
    public ResponseEntity<String> paymentReturn(HttpServletRequest request) {
        boolean valid = vnPayService.verifyPayment(request);
        String txnRef = request.getParameter("vnp_TxnRef");
        String responseCode = request.getParameter("vnp_ResponseCode");
        String transactionStatus = request.getParameter("vnp_TransactionStatus");

        try {
            Long paymentId = Long.parseLong(txnRef);

            if (valid && "00".equals(responseCode) && "00".equals(transactionStatus)) {
                paymentService.updatePaymentStatus(paymentId, "COMPLETED");
                return ResponseEntity.ok("Payment successful");
            } else {
                paymentService.updatePaymentStatus(paymentId, "FAILED");
                return ResponseEntity.ok("Payment failed: " +
                        "responseCode=" + responseCode + ", transactionStatus=" + transactionStatus);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid transaction reference: " + txnRef);
        }
    }

    @PostMapping("/refund")
    public ResponseEntity<String> refund(@RequestBody RefundRequest refundRequest) {
        String response = vnPayService.refundTransaction(refundRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/query")
    public ResponseEntity<String> queryTransaction(@RequestParam("vnp_TxnRef") String txnRef) {
        String result = vnPayService.queryTransaction(txnRef);
        return ResponseEntity.ok(result);
    }
}
