package com.coding.payment.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "party")
public class Party {
    @JsonProperty("account_name")
    String accountName;
    @Id
    @JsonProperty("account_number")
    String accountNumber;
    @JsonProperty("account_number_code")
    String accountNumberCode;
    @JsonProperty("account_type")
    Integer accountType;
    String address;
    @JsonProperty("bank_id")
    String bankId;
    @JsonProperty("bank_id_code")
    String bankIdCode;
    String name;
}