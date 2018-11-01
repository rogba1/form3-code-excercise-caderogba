package com.coding.payment.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class ChargesInformation {
    @JsonProperty("bearer_code")
    String bearerCode;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonProperty("sender_charges")
    List<Charges> senderCharges;
    @JsonProperty("receiver_charges_amount")
    String receiverChargesAmount;
    @JsonProperty("receiver_charges_currency")
    String receiverChargesCurrency;
}


