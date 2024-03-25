package com.shivraj.PaymentService.service;

import com.shivraj.PaymentService.entity.TransactionDetails;
import com.shivraj.PaymentService.model.PaymentMode;
import com.shivraj.PaymentService.model.PaymentRequest;
import com.shivraj.PaymentService.repository.TransactionDetailsRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private TransactionDetailsRepo transactionDetailsRepo;

    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording Payment Details {}",paymentRequest);

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();

        transactionDetailsRepo.save(transactionDetails);
        log.info("Transactions completed with transaction id: {}",transactionDetails.getId());
        return transactionDetails.getId();
    }
}
