package com.test.payment.paymenttest.helpers;

import com.test.payment.paymenttest.enums.ErrorHttpEnum;

public class CommonException extends Exception {
    /**
     * constructor
     * 
     * @param errorMessage
     * @param errorHttpEnum
     */
    public CommonException(String errorMessage, ErrorHttpEnum errorHttpEnum) {
        super(
            new StringBuilder(errorMessage)
                .append(": ")
                .append(errorHttpEnum.getLevel())
                .toString()
        );
    }

    /**
     * getSeverity to get severity level from error message
     * 
     * @param message String, expected contains severity level
     * @return String
     */
    public static String getSeverity(String message) {
        return ErrorHttpEnum.getLevelFromMsg(message);
    }

    /**
     * getErrorMsg to get error message from error message
     * 
     * @param message String
     * @return String
     */
    public static String getErrorMsg(String message) {
        return message.split(": ")[0];
    }
}