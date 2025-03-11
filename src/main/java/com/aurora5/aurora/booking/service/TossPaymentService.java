package com.aurora5.aurora.booking.service;

public interface TossPaymentService {
    boolean verifyPayment(String paymentKey, String orderId, int amount);
}
