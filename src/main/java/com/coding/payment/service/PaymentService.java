package com.coding.payment.service;


import com.coding.payment.model.Payment;
import com.coding.payment.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public List<Payment> getAllPayments() {
        log.info("getAllPayments");
        return paymentRepository.findAll();
    }

    @Transactional
    public Payment getPayment(final String paymentId) {
        log.info("getPayment {}", paymentId);
        return paymentRepository.findById(paymentId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void deletePayment(final String paymentId) {
        log.info("deletePayment {}", paymentId);
        final Optional<Payment> paymentToDelete = paymentRepository.findById(paymentId);
        paymentRepository.delete(paymentToDelete.orElseThrow(EntityNotFoundException::new));
    }

    @Transactional
    public Payment updatePayment(final String paymentId, final Payment payment) {
        log.info("updatePayment {}", paymentId);
        final Optional<Payment> existingPayment = paymentRepository.findById(paymentId);
        if(!existingPayment.isPresent()){
            throw new EntityNotFoundException();
        }

        payment.setId(existingPayment.get().getId());
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment createPayment(final Payment payment) {
        final Payment savedPayment = paymentRepository.save(payment);
        log.info("createPayment {}", savedPayment.getId());

        return savedPayment;
    }
}
