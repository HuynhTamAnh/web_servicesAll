package com.example.ss7.service;

import com.example.ss7.entity.PaymentSlip;
import com.example.ss7.repository.PaymentSlipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentSlipService {

    @Autowired
    private PaymentSlipRepository paymentSlipRepository;

    public List<PaymentSlip> getAllPaymentSlips() {
        return paymentSlipRepository.findAll();
    }

    public Optional<PaymentSlip> getPaymentSlipById(Long id) {
        return paymentSlipRepository.findById(id);
    }

    public PaymentSlip addPaymentSlip(PaymentSlip paymentSlip) {
        return paymentSlipRepository.save(paymentSlip);
    }
}