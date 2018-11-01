package com.coding.payment.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Fx {
    @JsonProperty("contract_reference")
    String contractReference;
    @JsonProperty("exchange_rate")
    String exchangeRate;
    @JsonProperty("original_amount")
    String originalAmount;
    @JsonProperty("original_currency")
    String originalCurrency;
}

