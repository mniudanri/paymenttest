package com.test.payment.paymenttest.helpers;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {
    public static <T> Map<String, Object> getCommonResponse(T data, String message) {
        Map<String, Object> response =  new HashMap<>();
        response.put("data", data);
        response.put("message", message);
    
        return response;
    }
}
