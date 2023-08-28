package com.test.payment.paymenttest.helpers;

import com.test.payment.paymenttest.enums.ErrorHttpEnum;

public class CommonException extends Exception {
    public CommonException(String errorMessage, ErrorHttpEnum errorHttpEnum) {
        super(
            new StringBuilder(errorMessage)
                .append(": ")
                .append(errorHttpEnum.getLevel())
                .toString()
        );
    }

    public static String getSeverity(String message) {
        return ErrorHttpEnum.getLevelFromMsg(message);
    }

    public static String getErrorMsg(String message) {
        return message.split(": ")[0];
    }
}