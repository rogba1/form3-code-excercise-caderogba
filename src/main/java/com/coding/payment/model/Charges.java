package com.coding.payment.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "charges")
public class Charges {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    Long chargeId;
    String amount;
    String currency;
}


