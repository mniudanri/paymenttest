package com.test.payment.paymenttest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.payment.paymenttest.entities.Payment;
import com.test.payment.paymenttest.repositories.PaymentRepository;

@Service
public class PaymentService {
    @Autowired PaymentRepository paymentRepository;

    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }
    
}
