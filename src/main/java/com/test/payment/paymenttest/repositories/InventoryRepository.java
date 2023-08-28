package com.test.payment.paymenttest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.payment.paymenttest.entities.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
}
