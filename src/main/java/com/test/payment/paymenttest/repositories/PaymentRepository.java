package com.test.payment.paymenttest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.payment.paymenttest.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
}
