package com.test.payment.paymenttest.constants;

// Severity stores severity level for errors
public class Severity {
    // LOW severity, an expected error such as: caused by validation or data not found
    public static final String LOW_HTTP_STATUS   = "LOW_SEVERITY";

    // HIGH severity, an unexpected error such as: Internal Server Error, db error, etc.
    public static final String HIGH_HTTP_STATUS  = "HIGH_SEVERITY";    
}
