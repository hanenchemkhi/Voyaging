package com.perscholas.voyaging.model;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;
import java.util.Set;

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

    @NonNull
    @Column(length = 16,name = "credit_card_number", unique = true)
    // figure out how to make it accept 15 or 16 digits only
    Long creditCardNumber;

    // need to check how to add month/year only
    LocalDate expirationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    Customer customer;




}
