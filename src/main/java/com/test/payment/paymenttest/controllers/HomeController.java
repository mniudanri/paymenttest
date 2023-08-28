package com.test.payment.paymenttest.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.payment.paymenttest.controllers.payloads.CreatePaymentRequest;
import com.test.payment.paymenttest.controllers.payloads.UpdatePaymentRequest;
import com.test.payment.paymenttest.helpers.ApiResponse;
import com.test.payment.paymenttest.services.PaymentService;

@RestController
public class HomeController {
    @Autowired PaymentService paymentService;

    // retrieve all payment records
    @GetMapping("payments")
    public Map<String, Object> getPayments() {
       return ApiResponse.getCommonResponse(paymentService.getPayments(), "SUCCESS");
    }

    // retrieve payment record by paymentId
    @GetMapping("payment/paymentId/{paymentId}")
    public Map<String, Object> getPaymentById( @PathVariable("paymentId") String id) {
         try {
            return ApiResponse.getCommonResponse(paymentService.getPaymentById(id), "SUCCESS");

        } catch (Exception e) {
            return ApiResponse.getCommonResponse(null, e.getMessage());
        }
    }

    // create payment record
    @PostMapping("payment")
    public Map<String, Object> createPayment(@RequestBody CreatePaymentRequest createRequest) {
        try {
            return ApiResponse.getCommonResponse(paymentService.createPayment(createRequest), "SUCCESS");

        } catch (Exception e) {
            return ApiResponse.getCommonResponse(null, e.getMessage());
        }
    }

    // update payment record
    @PutMapping("payment")
    public Map<String, Object> updatePayment(@RequestBody UpdatePaymentRequest updateRequest) {
         try {
            return ApiResponse.getCommonResponse(paymentService.updatePayment(updateRequest), "SUCCESS");

        } catch (Exception e) {
            return ApiResponse.getCommonResponse(null, e.getMessage());
        }
    }

    // delete payment record
    @DeleteMapping("payment/paymentId/{paymentId}")
    public Map<String, Object> updatePayment(@PathVariable("paymentId") String paymentId) {
         try {
            return ApiResponse.getCommonResponse(paymentService.deletePayment(paymentId), "SUCCESS");

        } catch (Exception e) {
            return ApiResponse.getCommonResponse(null, e.getMessage());
        }
    }
}
