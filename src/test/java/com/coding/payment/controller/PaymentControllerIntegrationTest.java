package com.coding.payment.controller;

import com.coding.payment.model.Payment;
import com.coding.payment.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerIntegrationTest {

    private static final Payment TEST_PAYMENT = Payment.builder().id("test-id").build();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void getPayments() throws Exception {
        final List<Payment> allPayments = Collections.singletonList(TEST_PAYMENT);

        given(paymentService.getAllPayments()).willReturn(allPayments);

        mvc.perform(get("/payments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(TEST_PAYMENT.getId())));
    }

    @Test
    public void getAPayment() throws Exception {
        given(paymentService.getPayment("test-id")).willReturn(TEST_PAYMENT);

        mvc.perform(get("/payments/test-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(TEST_PAYMENT.getId())));
    }

    @Test
    public void createAPayment() throws Exception {
        given(paymentService.createPayment(any())).willReturn(TEST_PAYMENT);

        mvc.perform(post("/payments")
                .content(asJsonString(TEST_PAYMENT))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(TEST_PAYMENT.getId())));
    }

    @Test
    public void updateAPayment() throws Exception {
        given(paymentService.updatePayment(eq("test-id"), any())).willReturn(TEST_PAYMENT);

        mvc.perform(put("/payments/test-id")
                .content(asJsonString(TEST_PAYMENT))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(TEST_PAYMENT.getId())));
    }

    @Test
    public void paymentNotFound() throws Exception {
        given(paymentService.getPayment("test-id")).willThrow(EntityNotFoundException.class);

        mvc.perform(get("/payments/test-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void paymentConflict() throws Exception {
        given(paymentService.updatePayment(eq("test-id"), any())).willThrow(IllegalStateException.class);

        mvc.perform(put("/payments/test-id")
                .content(asJsonString(TEST_PAYMENT))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    public static String asJsonString(final Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}