package com.test.payment.paymenttest.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.payment.paymenttest.constants.Message;
import com.test.payment.paymenttest.controllers.payloads.CreatePaymentRequest;
import com.test.payment.paymenttest.controllers.payloads.UpdatePaymentRequest;
import com.test.payment.paymenttest.controllers.responses.DeletePaymentResponse;
import com.test.payment.paymenttest.entities.Inventory;
import com.test.payment.paymenttest.entities.Payment;
import com.test.payment.paymenttest.entities.PaymentType;
import com.test.payment.paymenttest.enums.ErrorHttpEnum;
import com.test.payment.paymenttest.enums.PaymentTypeEnum;
import com.test.payment.paymenttest.helpers.CommonException;
import com.test.payment.paymenttest.repositories.InventoryRepository;
import com.test.payment.paymenttest.repositories.PaymentRepository;
import com.test.payment.paymenttest.repositories.PaymentTypeRepository;

@Service
public class PaymentService {
    @Autowired PaymentRepository paymentRepository;
    @Autowired PaymentTypeRepository paymentTypeRepository;
    @Autowired InventoryRepository inventoryRepository;



    public List<Payment> getPayments(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Payment> pagedResult = paymentRepository.findAll(paging);

        return pagedResult.toList();
    }

    public Payment getPaymentById(String id) throws Exception {
        Optional<Payment> paymentOpt = paymentRepository.findById(Long.parseLong(id));
        
        if (paymentOpt.isEmpty()) {
            throw new CommonException(Message.PAYMENT_NOT_FOUND, ErrorHttpEnum.BAD_REQUEST);
        }

        return paymentOpt.get();
    }

    public Payment createPayment(CreatePaymentRequest request) throws Exception {
        if (request.getCustomerId() == 0L 
                || request.getItemId() == 0L
                || request.getPaymentTypeId() == 0L) {
            throw new CommonException(Message.INVALID_REQUEST, ErrorHttpEnum.BAD_REQUEST);
        }
        
        // find the payment type
        Optional<PaymentType> paymentTypeOpt = paymentTypeRepository.findById(request.getPaymentTypeId());

        if (paymentTypeOpt.isEmpty()) {
            // handling invalid payment type
            throw new CommonException(Message.PAYMENT_TYPE_NOT_FOUND, ErrorHttpEnum.BAD_REQUEST);
        }

        String typeName = paymentTypeOpt.get().getTypeName();
        Payment createPayment = null;

        switch (typeName) {
            case "BALANCE":
                Optional<Inventory> inventoryOpt = inventoryRepository.findById(request.getItemId());
                if (inventoryOpt.isEmpty()) {
                    // return handling invalid item
                    throw new CommonException(Message.ITEM_INVENTORY_NOT_FOUND, ErrorHttpEnum.BAD_REQUEST);
                } else if (inventoryOpt.get().getQuantity() < 1) {
                    // check quantity
                    throw new CommonException(Message.ITEM_OUT_OF_STOCK, ErrorHttpEnum.BAD_REQUEST);
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
                throw new CommonException(Message.UNSUPPORTED_PAYMENT_TYPE, ErrorHttpEnum.BAD_REQUEST);
        }

        return createPayment;
    }

    public Payment updatePayment(UpdatePaymentRequest request) throws Exception {
        Optional<Payment> paymentOpt = paymentRepository.findById(request.getPaymentId());
        
        if (paymentOpt.isEmpty()) {
            throw new CommonException(Message.PAYMENT_NOT_FOUND, ErrorHttpEnum.BAD_REQUEST);
        }

        Payment payment = paymentOpt.get();

        // check payment type
        if (request.getPaymentTypeId() != 0L
                && request.getPaymentTypeId() != payment.getPaymentTypeId()) {
            Optional<PaymentType> paymentTypeOpt = paymentTypeRepository.findById(request.getPaymentTypeId());
            
            // balance code
            String balance = PaymentTypeEnum.BALANCE.getCode();

            // validate request's paymentType,
            // and accept only Balance
            if (paymentTypeOpt.isEmpty()) {
                throw new CommonException(Message.PAYMENT_TYPE_NOT_FOUND, ErrorHttpEnum.BAD_REQUEST);

            } else if (!paymentTypeOpt.get().getTypeName().equals(balance)) {
                throw new CommonException(Message.UNSUPPORTED_PAYMENT_TYPE, ErrorHttpEnum.BAD_REQUEST);

            }

            // set new paymentType
            payment.setPaymentTypeId(request.getPaymentTypeId());
        }
        
        // check item inventory
        if (request.getItemId() != 0L) {
            Optional<Inventory> inventoryOpt = inventoryRepository.findById(request.getItemId());
            if (inventoryOpt.isEmpty()) {
                // return handling invalid item
                throw new CommonException(Message.ITEM_INVENTORY_NOT_FOUND, ErrorHttpEnum.BAD_REQUEST);

            } else if (inventoryOpt.get().getQuantity() < 1) {
                // check quantity
                throw new CommonException(Message.ITEM_OUT_OF_STOCK, ErrorHttpEnum.BAD_REQUEST);

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

    public DeletePaymentResponse deletePayment(String id) throws Exception {
        Optional<Payment> paymentOpt = paymentRepository.findById(Long.parseLong(id));
        
        if (paymentOpt.isEmpty()) {
            throw new CommonException(Message.PAYMENT_NOT_FOUND, ErrorHttpEnum.BAD_REQUEST);

        }

        paymentRepository.deleteById(Long.parseLong(id));
        
        DeletePaymentResponse deletePaymentResponse = new DeletePaymentResponse();
        deletePaymentResponse.setPaymentId(id);

        return deletePaymentResponse;
    }
}
