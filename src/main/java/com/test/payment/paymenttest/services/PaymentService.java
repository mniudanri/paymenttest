package com.test.payment.paymenttest.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.payment.paymenttest.constants.Errors;
import com.test.payment.paymenttest.controllers.payloads.CreatePaymentRequest;
import com.test.payment.paymenttest.controllers.payloads.UpdatePaymentRequest;
import com.test.payment.paymenttest.entities.Inventory;
import com.test.payment.paymenttest.entities.Payment;
import com.test.payment.paymenttest.entities.PaymentType;
import com.test.payment.paymenttest.repositories.InventoryRepository;
import com.test.payment.paymenttest.repositories.PaymentRepository;
import com.test.payment.paymenttest.repositories.PaymentTypeRepository;

@Service
public class PaymentService {
    @Autowired PaymentRepository paymentRepository;
    @Autowired PaymentTypeRepository paymentTypeRepository;
    @Autowired InventoryRepository inventoryRepository;



    public List<Payment> getPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(String id) throws Exception {
        Optional<Payment> paymentOpt = paymentRepository.findById(Long.parseLong(id));
        
        if (paymentOpt.isEmpty()) {
            throw new Exception(Errors.PAYMENT_NOT_FOUND);
        }

        return paymentOpt.get();
    }

    public Payment createPayment(CreatePaymentRequest request) throws Exception {
        if (request.getCustomerId() == 0L 
                || request.getItemId() == 0L
                || request.getPaymentTypeId() == 0L) {
            throw new Exception(Errors.INVALID_REQUEST);
        }
        
        // find the payment type
        Optional<PaymentType> paymentTypeOpt = paymentTypeRepository.findById(request.getPaymentTypeId());

        if (paymentTypeOpt.isEmpty()) {
            // handling invalid payment type
            throw new Exception(Errors.PAYMENT_TYPE_NOT_FOUND);
        }

        String typeName = paymentTypeOpt.get().getTypeName();
        Payment createPayment = null;

        switch (typeName) {
            case "BALANCE":
                Optional<Inventory> inventoryOpt = inventoryRepository.findById(request.getItemId());
                if (inventoryOpt.isEmpty()) {
                    // return handling invalid item
                    throw new Exception(Errors.ITEM_INVENTORY_NOT_FOUND);
                } else if (inventoryOpt.get().getQuantity() < 1) {
                    // check quantity
                    throw new Exception(Errors.ITEM_OUT_OF_STOCK);
                }
                
                createPayment = new Payment(inventoryOpt.get().getPrice(), request.getPaymentTypeId(),
                                                    new Date(), request.getCustomerId());
                
                // create new payment
                paymentRepository.save(createPayment);

                // update inventory
                Inventory inventory = inventoryOpt.get();
                inventory.setQuantity(inventory.getQuantity() - 1);
                inventoryRepository.save(inventory);

                break;
             
            default:
                // unsupported paymentType
                throw new Exception(Errors.UNSUPPORTED_PAYMENT_TYPE);
        }

        return createPayment;
    }

    public Payment updatePayment(UpdatePaymentRequest request) throws Exception {
        Optional<Payment> paymentOpt = paymentRepository.findById(request.getPaymentId());
        
        if (paymentOpt.isEmpty()) {
            throw new Exception(Errors.PAYMENT_NOT_FOUND);
        }

        Payment payment = paymentOpt.get();

        // check payment type
        if (request.getPaymentTypeId() != 0L
                && request.getPaymentTypeId() != payment.getPaymentTypeId()) {
            Optional<PaymentType> paymentTypeOpt = paymentTypeRepository.findById(request.getPaymentTypeId());

            // validate request's paymentType
            if (paymentTypeOpt.isEmpty()) {
                // handling invalid payment type
                throw new Exception(Errors.PAYMENT_TYPE_NOT_FOUND);
            } else if (!paymentTypeOpt.get().getTypeName().equals("BALANCE")) {
                // currently support only balance
                throw new Exception(Errors.UNSUPPORTED_PAYMENT_TYPE);
            }

            // set new paymentType
            payment.setPaymentTypeId(request.getPaymentTypeId());
        }
        
        // check item inventory
        if (request.getItemId() != 0L) {
            Optional<Inventory> inventoryOpt = inventoryRepository.findById(request.getItemId());
            if (inventoryOpt.isEmpty()) {
                // return handling invalid item
                throw new Exception(Errors.ITEM_INVENTORY_NOT_FOUND);
            } else if (inventoryOpt.get().getQuantity() < 1) {
                // check quantity
                throw new Exception(Errors.ITEM_OUT_OF_STOCK);
            }

            // update inventory record
            Inventory inventory = inventoryOpt.get();
            inventory.setQuantity(inventory.getQuantity() - 1);
            inventoryRepository.save(inventory);

            // set new amount item
            payment.setAmount(request.getItemId());
        }
        
        // update payment record
        paymentRepository.save(payment);
        
        return paymentOpt.get();
    }

    public String deletePayment(String id) throws Exception {
        Optional<Payment> paymentOpt = paymentRepository.findById(Long.parseLong(id));
        
        if (paymentOpt.isEmpty()) {
            throw new Exception(Errors.PAYMENT_NOT_FOUND);
        }

        paymentRepository.deleteById(Long.parseLong(id));
        
        return "SUCCESS";
    }
}
