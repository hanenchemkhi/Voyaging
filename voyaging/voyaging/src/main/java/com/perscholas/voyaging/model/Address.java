package com.perscholas.voyaging.model;
import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Nationalized;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    String street;
    String country;
    Integer zipCode;
    @OneToOne(mappedBy = "address")
    Customer customer;
}
