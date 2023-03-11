package com.perscholas.voyaging.model;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.CreditCardNumber;


import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;
    @NotBlank(message ="Please enter credit card number")
    @CreditCardNumber
    String cardNumber;
    @NotBlank(message ="Please enter credit card holder name")
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    String cardholderName;
    @NotBlank(message ="Please select expiration month")
    String monthExpiration;
    @NotBlank(message ="Please select expiration year")
    String yearExpiration;

    @OneToOne(mappedBy = "card")
    Customer customer;

}
