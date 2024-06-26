package com.shivraj.OrderService.external.client;

import com.shivraj.OrderService.exception.CustomException;
import com.shivraj.OrderService.external.client.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker(name = "external",fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest PaymentRequest);

    default void fallback(Exception e){
        throw new CustomException("Payment service is not acceptable",500,"UNAVAILABLE");
    }

}
