package com.erp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class RazorpayVerificationService {

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public boolean verifySignature(String orderId,
                                   String paymentId,
                                   String razorpaySignature) {

        try {
            String payload = orderId + "|" + paymentId;

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey =
                    new SecretKeySpec(keySecret.getBytes(), "HmacSHA256");

            mac.init(secretKey);
            byte[] digest = mac.doFinal(payload.getBytes());

            String generatedSignature =
                    Base64.getEncoder().encodeToString(digest);

            return generatedSignature.equals(razorpaySignature);

        } catch (Exception e) {
            return false;
        }
    }
}
