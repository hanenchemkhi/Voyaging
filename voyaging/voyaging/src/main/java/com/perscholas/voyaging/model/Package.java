package com.perscholas.voyaging.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    @OneToMany(mappedBy="reservationPackage")
    Set<Reservation> reservations;

    @Enumerated(EnumType.STRING)
    PackageCategory packageCategory;
    Long price;
    LocalDate date;
}
