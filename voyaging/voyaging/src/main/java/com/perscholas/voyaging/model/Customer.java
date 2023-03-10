package com.perscholas.voyaging.model;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Nationalized;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Customer extends User{
    @NotBlank(message="Please enter phone number")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Phone number must be 10 digits")
    String phone;


    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id")
    Address address;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = CreditCard.class)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    CreditCard card ;

    @OneToMany(mappedBy="customer", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,targetEntity = Reservation.class)
    Set<Reservation> reservations = new HashSet<>();


    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setCustomer(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setCustomer(null);
    }



}
