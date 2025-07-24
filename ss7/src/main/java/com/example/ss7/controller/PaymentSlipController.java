package com.example.ss7.controller;

import com.example.ss7.entity.PaymentSlip;
import com.example.ss7.service.PaymentSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paymentslips")
public class PaymentSlipController {

    @Autowired
    private PaymentSlipService paymentSlipService;

    @GetMapping
    public ResponseEntity<List<PaymentSlip>> getAllPaymentSlips() {
        return new ResponseEntity<>(paymentSlipService.getAllPaymentSlips(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PaymentSlip> addPaymentSlip(@RequestBody PaymentSlip paymentSlip) {
        return new ResponseEntity<>(paymentSlipService.addPaymentSlip(paymentSlip), HttpStatus.CREATED);
    }
}