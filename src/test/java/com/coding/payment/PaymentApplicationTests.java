package com.coding.payment;

import com.coding.payment.model.Payment;
import com.coding.payment.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PaymentApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class PaymentApplicationTests {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PaymentRepository repository;


    @Test
    public void createRetrieveAndDeletePayments()
            throws Exception {
        final Payment createdPayment = createPayment();

        mvc.perform(get("/payments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].organisation_id", is(createdPayment.getOrganisationId())));

        mvc.perform(get(String.format("/payments/%s", createdPayment.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.organisation_id", is(createdPayment.getOrganisationId())));

        mvc.perform(delete(String.format("/payments/%s", createdPayment.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    @Test
    public void createAndUpdatePayment()
            throws Exception {
        final Payment createdPayment = createPayment();
        createdPayment.setVersion(2);
        final String updatedPayment = OBJECT_MAPPER.writeValueAsString(createdPayment);

        mvc.perform(put(String.format("/payments/%s", createdPayment.getId()))
                .content(updatedPayment)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.version", is(2)))
                .andReturn();
        mvc.perform(get(String.format("/payments/%s", createdPayment.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.version", is(2)))
                .andExpect(jsonPath("$.id", is(createdPayment.getId())));

        mvc.perform(delete(String.format("/payments/%s", createdPayment.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get("/payments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }


    @Test
    public void notFound()
            throws Exception {
        mvc.perform(delete("/payments/bad-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mvc.perform(get("/payments/bad-id")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mvc.perform(put("/payments/bad-id")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    private Payment createPayment() throws Exception {
        final File file = ResourceUtils.getFile("classpath:payment.json");
        final byte[] bytes = Files.readAllBytes(Paths.get(file.getPath()));

        final MvcResult mvcResult = mvc.perform(post("/payments")
                .content(bytes)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.organisation_id", is("743d5b63-8e6f-432e-a8fa-c5d8d2ee5fcb")))
                .andReturn();
        return OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), Payment.class);
    }
}
