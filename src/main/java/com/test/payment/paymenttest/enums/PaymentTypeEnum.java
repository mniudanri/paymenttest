package com.test.payment.paymenttest.enums;

public enum PaymentTypeEnum {
    // Balance
    BALANCE("BALANCE", "Balance"),

    // CC
    CREDIT_CARD("CREDIT_CARD", "Credit Card"),
    
    // DC
    DEBIT_CARD("DEBIT_CARD", "Debit Card"),
    
    // Coupon / Voucher
    COUPON("COUPON", "Coupon")
    
    // etc...
    ;


    private final String description;

    private final String code;



    // private enum constructor
    private PaymentTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }
}
