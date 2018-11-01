package com.coding.payment.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Attributes {
    String amount;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("beneficiary_party")
    Party beneficiaryParty;
    @JsonProperty("charges_information")
    ChargesInformation chargesInformation;
    String currency;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("debtor_party")
    Party debtorParty;
    @JsonProperty("end_to_end_reference")
    String endToEndReference;
    Fx fx;
    @JsonProperty("numeric_reference")
    String numericReference;
    @JsonProperty("payment_id")
    String paymentId;
    @JsonProperty("payment_purpose")
    String paymentPurpose;
    @JsonProperty("payment_scheme")
    String paymentScheme;
    @JsonProperty("payment_type")
    String paymentType;
    @JsonProperty("processing_date")
    String processingDate;
    String reference;
    @JsonProperty("scheme_payment_sub_type")
    String schemePaymentSubType;
    @JsonProperty("scheme_payment_type")
    String schemePaymentType;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("sponsor_party")
    Party sponsorParty; //maybe?
}
