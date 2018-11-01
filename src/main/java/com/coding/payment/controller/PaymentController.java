package com.coding.payment.controller;

import com.coding.payment.model.Payment;
import com.coding.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payments")
    public List<Payment> getPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/payments/{paymentId}")
    public Payment getPayment(@PathVariable final String paymentId) {
        return paymentService.getPayment(paymentId);
    }

    @PostMapping("/payments")
    @ResponseStatus(HttpStatus.CREATED)
    public Payment createPayment(@RequestBody final Payment payment) {
        return paymentService.createPayment(payment);
    }

    @PutMapping("/payments/{paymentId}")
    public Payment updatePayment(@PathVariable final String paymentId, @RequestBody Payment payment) {
        return paymentService.updatePayment(paymentId, payment);
    }

    @DeleteMapping("/payments/{paymentId}")
    public void deletePayment(@PathVariable final String paymentId) {
        paymentService.deletePayment(paymentId);
    }
}
