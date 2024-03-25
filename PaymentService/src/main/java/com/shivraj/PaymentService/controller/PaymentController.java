package com.shivraj.PaymentService.controller;

import com.shivraj.PaymentService.model.PaymentMode;
import com.shivraj.PaymentService.model.PaymentRequest;
import com.shivraj.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest PaymentRequest){
        return new ResponseEntity<>(paymentService.doPayment(PaymentRequest), HttpStatus.OK);
    }

}
