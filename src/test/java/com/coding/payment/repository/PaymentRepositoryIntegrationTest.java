package com.coding.payment.repository;

import com.coding.payment.model.Payment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PaymentRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void returnsAllPayments() {
        final Payment payment = Payment.builder().organisationId("test-id").build();
        entityManager.merge(payment);
        entityManager.flush();

        final List<Payment> all = paymentRepository.findAll();

        assertThat(all.get(0).getOrganisationId()).isEqualTo(payment.getOrganisationId());
    }
}