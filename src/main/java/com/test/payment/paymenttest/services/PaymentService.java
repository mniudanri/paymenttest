package com.test.payment.paymenttest.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.test.payment.paymenttest.constants.CommonConstant;
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
import com.test.payment.paymenttest.helpers.DateHelper;
import com.test.payment.paymenttest.repositories.InventoryRepository;
import com.test.payment.paymenttest.repositories.PaymentRepository;
import com.test.payment.paymenttest.repositories.PaymentTypeRepository;

@Service
public class PaymentService {
    @Autowired PaymentRepository paymentRepository;

    @Autowired PaymentTypeRepository paymentTypeRepository;

    @Autowired InventoryRepository inventoryRepository;

    /**
     * getPayments for getting payment records when access API GET payments,
     * see {@link com.test.payment.paymenttest.controllers.HomeController#getPayments} 
     * 
     * @param customerId int
     * @param paymentTypeId int
     * @param strDate Optional<String>
     * @param paymentId Optional<Integer>
     * @param page int
     * @param size int
     * @return List Payment
     * @throws CommonException
     */
    public List<Payment> getPayments(int customerId, int paymentTypeId, Optional<String> strDate, Optional<Integer> paymentId, int page, int size) throws CommonException {
        Date temp;
        Optional<Date> date = ((temp = DateHelper.getDateFromStr(strDate)) != null) ? Optional.of(temp) : Optional.empty();
        
        Pageable paging;
        Slice<Payment> slicedResult;

        List<Payment> paymentList = new ArrayList<>();
        
        int maxSize = CommonConstant.MAX_BATCH_SIZE;
        int currSize = size;
        int currPage = page;
        int processedSize = 0;

        // handling big size request
        // check max size per batch at CommonConstant#MAX_BATCH_SIZE
        do {
            // check and update current size
            currSize = (currSize >= maxSize) ? maxSize : (size - processedSize);

            // init pagination
            paging = PageRequest.of(currPage, currSize);

            // query
            slicedResult = paymentRepository.findAllByCustomerIdAndPaymentTypeIdAndDateAndId(customerId, paymentTypeId, date, paymentId, paging);
            
            // add to list result
            paymentList.addAll(slicedResult.getContent());

            // update page and total proccesed size
            processedSize += currSize;
            currPage++;

        } while (processedSize < size && !slicedResult.isEmpty());


        return paymentList;
    }

    /**
     * getPaymentById for getting a payment record when access API GET payment by ID,
     * see {@link com.test.payment.paymenttest.controllers.HomeController#getPaymentById} 
     * 
     * @param id
     * @return Payment
     * @throws Exception
     */
    public Payment getPaymentById(String id) throws Exception {
        Optional<Payment> paymentOpt = paymentRepository.findById(Long.parseLong(id));
        
        if (paymentOpt.isEmpty()) {
            throw new CommonException(Message.PAYMENT_NOT_FOUND, ErrorHttpEnum.BAD_REQUEST);
        }

        return paymentOpt.get();
    }

    /**
     * 
     * getPaymentById for creating a payment record when access API POST payment,
     * see {@link com.test.payment.paymenttest.controllers.HomeController#createPayment} 
     * 
     * @param request
     * @return Payment
     * @throws Exception
     */
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

    /**
     * 
     * getPaymentById for updating a payment record when access API PUT payment,
     * see {@link com.test.payment.paymenttest.controllers.HomeController#updatePayment} 
     * 
     * @param request
     * @return Payment
     * @throws Exception
     */
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

            } else if (request.getCustomerId() != paymentOpt.get().getCustomerId()) {
                throw new CommonException(Message.CUSTOMER_ID_CANNOT_BE_DIFFERENT, ErrorHttpEnum.BAD_REQUEST);

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
            payment.setAmount(inventory.getPrice());
        }
        
        // update payment record
        paymentRepository.save(payment);
        
        return paymentOpt.get();
    }

    /**
     * 
     * getPaymentById for deleting a payment record when access API DELETE payment,
     * see {@link com.test.payment.paymenttest.controllers.HomeController#deletePayment} 
     * 
     * @param id
     * @return DeletePaymentResponse contains deleted ID
     * @throws Exception
     */
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
