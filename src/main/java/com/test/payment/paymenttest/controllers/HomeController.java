package com.test.payment.paymenttest.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, Object>> getPayments() {
       return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.getCommonResponse(paymentService.getPayments(), null));
    }

    // retrieve payment record by paymentId
    @GetMapping("payment/paymentId/{paymentId}")
    public ResponseEntity<Map<String, Object>> getPaymentById( @PathVariable("paymentId") String id) {
         try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.getCommonResponse(paymentService.getPaymentById(id), null));

        } catch (Exception e) {
            return ApiResponse.getErrorResponse(HttpStatus.BAD_REQUEST, e);
        }
    }

    // create payment record
    @PostMapping("payment")
    public ResponseEntity<Map<String, Object>> createPayment(@RequestBody CreatePaymentRequest createRequest) {
        try {
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.getCommonResponse(paymentService.createPayment(createRequest), null));

        } catch (Exception e) {
                return ApiResponse.getErrorResponse(HttpStatus.BAD_REQUEST, e);

        }
    }

    // update payment record
    @PutMapping("payment")
    public ResponseEntity<Map<String, Object>> updatePayment(@RequestBody UpdatePaymentRequest updateRequest) {
         try {
            
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.getCommonResponse(paymentService.updatePayment(updateRequest), null));

        } catch (Exception e) {
            return ApiResponse.getErrorResponse(HttpStatus.BAD_REQUEST, e);

        }
    }

    // delete payment record
    @DeleteMapping("payment/paymentId/{paymentId}")
    public ResponseEntity<Map<String, Object>> updatePayment(@PathVariable("paymentId") String paymentId) {
         try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.getCommonResponse(paymentService.deletePayment(paymentId), null));
        } catch (Exception e) {
            return ApiResponse.getErrorResponse(HttpStatus.BAD_REQUEST, e);

        }
    }
}
