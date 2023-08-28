package com.test.payment.paymenttest.controllers.payloads;

public class CreatePaymentRequest {
    // paymentType: BALANCE, etc.
    private long paymentTypeId;

    // item to be paid
    private long itemId;
    
    // customerId / userId
    private long customerId;

    public long getPaymentTypeId() {
        return paymentTypeId;
    }
    public void setPaymentTypeId(long paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }
    public long getItemId() {
        return itemId;
    }
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
    public long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    
}
