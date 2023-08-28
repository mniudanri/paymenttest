package com.test.payment.paymenttest.enums;

import org.springframework.http.HttpStatus;

import com.test.payment.paymenttest.constants.Severity;

public enum ErrorHttpEnum {
    // Bad Request
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), Severity.LOW_HTTP_STATUS),
    
    // Not Found
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), Severity.LOW_HTTP_STATUS),
    
    // Bad Gateway
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY.value(), Severity.HIGH_HTTP_STATUS),

    // Service Unavailable
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), Severity.HIGH_HTTP_STATUS),
    
    // ...
    ;

    private final String level;

    private final int code;

    // private enum constructor
    private ErrorHttpEnum(int code, String level) {
        this.code = code;
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public int getCode() {
        return code;
    }

    /**
     * get level severity from error message
     * 
     * @param msg String which contains Severity level
     * @return String level
     */
    public static String getLevelFromMsg(String msg) {
        // if no level in message, considered as unexpected error with High severity
        return msg.contains(Severity.LOW_HTTP_STATUS)
            ? Severity.LOW_HTTP_STATUS
            : Severity.HIGH_HTTP_STATUS;
    }
}
