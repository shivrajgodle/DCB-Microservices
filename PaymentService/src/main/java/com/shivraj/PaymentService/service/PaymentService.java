package com.shivraj.PaymentService.service;


import com.shivraj.PaymentService.model.PaymentRequest;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);
}
