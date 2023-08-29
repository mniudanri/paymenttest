package com.test.payment.paymenttest.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.payment.paymenttest.controllers.payloads.CreatePaymentRequest;
import com.test.payment.paymenttest.controllers.payloads.UpdatePaymentRequest;
import com.test.payment.paymenttest.helpers.ApiResponse;
import com.test.payment.paymenttest.services.PaymentService;

@RestController
public class HomeController {
    @Autowired PaymentService paymentService;

    /**
     * getPayments API with path /payments?page=&size=&customerId&paymentTypeId&date=&paymentId=, Method GET
     * get all payment records
     * 
     * mandatory parameter: customerId, paymentTypeId
     * mandatory parameter: paymentTypeId, paymentId
     * 
     * @param page int
     * @param size int
     * @param customerId int
     * @param paymentTypeId int
     * @param date Optional<String>
     * @param paymentId Optional<Integer>
     * @return ResponseEntity
     */
    @GetMapping("payments")
    public ResponseEntity<Map<String, Object>> getPayments(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "5") int size,
        
        // required parameters such as: customerId and paymentTypeId
        @RequestParam("customerId") int customerId,
        @RequestParam("paymentTypeId") int paymentTypeId,

        // optional parameters such as: date and paymentId
        @RequestParam("date") Optional<String> date,
        @RequestParam("paymentId") Optional<Integer> paymentId
    ) {
        try {
            return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.getCommonResponse(paymentService.getPayments(customerId, paymentTypeId, date, paymentId, page, size), null));

        } catch (Exception e) {
            return ApiResponse.getErrorResponse(HttpStatus.BAD_REQUEST, e);
        }
    }

    /**
     * /**
     * getPaymentById API with path /payment/paymentId/:paymentId, Method GET
     * get a payment record by specific paymentId
     * 
     * @param id String
     * @return ResponseEntity
     */
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

    /**
     *
     * createPayment API with path /payment, Method POST
     * create a payment record for specific item
     * 
     * @param createRequest CreatePaymentRequest
     * @return ResponseEntity
     */
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

    /**
     * 
     * updatePayment API with path /payment, Method PUT
     * update a payment record by specific paymentId
     * 
     * @param updateRequest UpdatePaymentRequest
     * @return ResponseEntity
     */
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

    /**
     * 
     * deletePayment API with path /payment/:paymentId, Method DELETE
     * delete a payment record by specific paymentId
     * 
     * @param paymentId String
     * @return ResponseEntity
     */
    @DeleteMapping("payment/paymentId/{paymentId}")
    public ResponseEntity<Map<String, Object>> deletePayment(@PathVariable("paymentId") String paymentId) {
         try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.getCommonResponse(paymentService.deletePayment(paymentId), null));
        } catch (Exception e) {
            return ApiResponse.getErrorResponse(HttpStatus.BAD_REQUEST, e);

        }
    }
}
