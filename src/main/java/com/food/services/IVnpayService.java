package com.food.services;

import com.food.request.RefundRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;

public interface IVnpayService {

    String createPaymentUrl(HttpServletRequest request, int amount, Long paymentId) throws UnsupportedEncodingException;

    boolean verifyPayment(HttpServletRequest request);

    String refundTransaction(RefundRequest request);

    String queryTransaction(String txnRef);

}
