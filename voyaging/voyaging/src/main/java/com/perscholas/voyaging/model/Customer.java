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
    @NotBlank(message = "Please enter first name")
    @Size(min=2, max= 50, message = "Please enter a valid name")
    String firstName;

    @Nationalized
    @NotBlank(message = "Please enter last name")
    @Size(min=2, max= 50, message = "Please enter a valid name")
    String lastName;



    @NotBlank(message = "Please enter email")
    @Column(unique = true)
    @Email(message="Please enter a valid email address")
    String email;


    @NotBlank(message="Password is required)")
    @Size(min=8, max=35, message = "Password length must be between 8 and 35 characters (special characters are permitted)")
    String password;

    @NotBlank(message="Confirm Password must not be blank")
    @Size(min=8, max=35, message = "Passwords didn’t match")
    @Transient
    private String confirmPassword;

    @NotBlank(message="Please enter phone number")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Phone number must be 10 digits")
    String phone;

//    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = Role.class)
//    @JoinColumn(name = "role_id", referencedColumnName = "id",nullable = false)
//    private Role role;


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

