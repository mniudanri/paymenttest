package com.test.payment.paymenttest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.payment.paymenttest.entities.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
    
}
