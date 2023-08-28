package com.test.payment.paymenttest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.payment.paymenttest.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
}
