package com.test.payment.paymenttest.helpers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.test.payment.paymenttest.constants.Message;
import com.test.payment.paymenttest.constants.Severity;

public class ApiResponse {
    public static <T> Map<String, Object> getCommonResponse(T data, String error) {
        Map<String, Object> response =  new HashMap<>();
        response.put("data", data);
        response.put("error", error);
    
        return response;
    }

    public static ResponseEntity<Map<String, Object>> getErrorResponse(HttpStatus defaultError, Exception e) {
        HttpStatus status = defaultError;
        String message = e.getMessage();

        // handle unexpected error with Bad Gateway status
        if (CommonException.getSeverity(e.getMessage())
                .equals(Severity.HIGH_HTTP_STATUS)) {

            status = HttpStatus.BAD_GATEWAY;
            message = Message.INTERNAL_SERVER_ERROR;
            e.printStackTrace();
        }
        
        return ResponseEntity
                .status(status)
                .body(ApiResponse.getCommonResponse(null, CommonException.getErrorMsg(message)));
    }
}
