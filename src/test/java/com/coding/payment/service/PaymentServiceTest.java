package com.coding.payment.service;

import com.coding.payment.model.Payment;
import com.coding.payment.repository.PaymentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@RunWith(SpringRunner.class)
public class PaymentServiceTest {

    public static final String TEST_ID = "test-id";
    private static final Payment TEST_PAYMENT = Payment.builder().id(TEST_ID).build();


    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentService underTest;

    @Before
    public void setUp() {
    }

    @Test
    public void getAllPayments() {
        doReturn(Collections.singletonList(TEST_PAYMENT)).when(paymentRepository).findAll();
        final List<Payment> all = underTest.getAllPayments();

        assertThat(all.get(0).getId()).isEqualTo(TEST_PAYMENT.getId());
    }

    @Test
    public void getAPayment() {
        doReturn(Optional.of(TEST_PAYMENT)).when(paymentRepository).findById(TEST_ID);
        final Payment payment = underTest.getPayment(TEST_ID);

        assertThat(payment.getId()).isEqualTo(TEST_PAYMENT.getId());
    }


    @Test(expected = EntityNotFoundException.class)
    public void deleteNotFound() {
        underTest.deletePayment(TEST_ID);
    }

    @Test
    public void update() {
        doReturn(Optional.of(TEST_PAYMENT)).when(paymentRepository).findById(TEST_ID);
        final Payment updatedPayment = Payment.builder().id(TEST_ID).version(3).build();
        doReturn(updatedPayment).when(paymentRepository).save(any(Payment.class));
        final Payment payment = underTest.updatePayment(TEST_ID, updatedPayment);

        assertThat(payment.getId()).isEqualTo(TEST_PAYMENT.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateNotFound() {
        doReturn(Optional.empty()).when(paymentRepository).findById(TEST_ID);
        final Payment payment = underTest.updatePayment(TEST_ID, Payment.builder().version(3).build());

        assertThat(payment.getId()).isEqualTo(TEST_PAYMENT.getId());
    }

    @Test
    public void create() {
        doReturn(TEST_PAYMENT).when(paymentRepository).save(TEST_PAYMENT);
        final Payment payment = underTest.createPayment(TEST_PAYMENT);

        assertThat(payment.getId()).isEqualTo(TEST_PAYMENT.getId());
    }


}