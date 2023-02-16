package com.perscholas.voyaging.model;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Nationalized;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    @NonNull
    @Nationalized
    String firstName;
    @NonNull
    @Nationalized
    String lastName;

    @NonNull
    @Column(length = 50,name = "email", unique = true)
    String email;
    @NonNull
    @Column(length = 50,name = "password")
    String password;
    @NonNull
    String phoneNumber;
    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    Address address;

    @NonNull
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL,orphanRemoval = true)
    Set<CreditCard> card = new HashSet<>();

  @NonNull
    @OneToMany(mappedBy="customer")
    Set<Reservation> reservations;
}
