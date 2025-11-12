package com.food.services.impl;

import com.food.request.RefundRequest;
import com.food.configurations.VnpayConfig;
import com.food.services.IVnpayService;
import com.food.utils.VnpayHelper;
import com.food.utils.VnpayUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class VnpayService implements IVnpayService {

    private final VnpayConfig vnpayConfig;

    public String createPaymentUrl(HttpServletRequest request, int amount, Long paymentId) throws UnsupportedEncodingException {
        String vnp_TxnRef = String.valueOf(paymentId);
        String vnp_IpAddr = VnpayUtils.getIpAddress(request);
        String vnp_CreateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnpayConfig.getTmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang");
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnpayConfig.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        try {
            String secureHash = VnpayHelper.hashAllFields(vnp_Params, vnpayConfig.getHashSecret());

            List<String> sortedKeys = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(sortedKeys);

            StringBuilder query = new StringBuilder();
            for (int i = 0; i < sortedKeys.size(); i++) {
                String key = sortedKeys.get(i);
                String value = vnp_Params.get(key);
                query.append(URLEncoder.encode(key, StandardCharsets.US_ASCII.toString()))
                        .append("=")
                        .append(URLEncoder.encode(value, StandardCharsets.US_ASCII.toString()));
                if (i != sortedKeys.size() - 1) query.append("&");
            }

            query.append("&vnp_SecureHash=").append(secureHash);
            return vnpayConfig.getPayUrl() + "?" + query.toString();

        } catch (Exception e) {
            log.error("Error creating VNPAY secure hash", e);
            throw new RuntimeException("Không thể tạo URL thanh toán", e);
        }
    }



    public boolean verifyPayment(HttpServletRequest request) {
        Map<String, String> fields = VnpayUtils.getFieldsFromRequest(request);
        String receivedHash = fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        log.info("Verifying VNPAY hash with fields: {}", fields);
        log.info("Received vnp_SecureHash: {}", receivedHash);

        try {
            String calculatedHash = VnpayHelper.hashAllFields(fields, vnpayConfig.getHashSecret());
            log.info("Calculated hash from fields: {}", calculatedHash);

            boolean isValid = receivedHash != null && receivedHash.equalsIgnoreCase(calculatedHash);
            log.info("Payment verification result: {}", isValid ? "VALID" : "INVALID");

            return isValid;
        } catch (Exception e) {
            log.error("Error verifying VNPAY secure hash", e);
            return false;
        }
    }

    public String refundTransaction(RefundRequest request) {
        // TODO: Tích hợp API của VNPAY theo tài liệu chính thức
        log.info("Refund request received: {}", request);
        return "Giao dịch hoàn tiền đã được xử lý (giả lập).";
    }

    public String queryTransaction(String txnRef) {
        // TODO: Gửi request đến API truy vấn của VNPAY
        log.info("Querying transaction with vnp_TxnRef: {}", txnRef);
        return "Thông tin giao dịch cho mã: " + txnRef + " (giả lập).";
    }
}
