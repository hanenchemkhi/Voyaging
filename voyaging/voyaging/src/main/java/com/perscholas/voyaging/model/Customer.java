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


    @Nationalized
    String firstName;

    @Nationalized
    String lastName;


    @Column(length = 50,name = "email", unique = true)
    String email;

    @Column(length = 50,name = "password")
    String password;

    String phone;

    @Enumerated(EnumType.STRING)
    Role role;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id")
    CreditCard card ;

    @OneToMany(mappedBy="customer")
    Set<Reservation> reservations;

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setCustomer(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setCustomer(null);
    }



}
