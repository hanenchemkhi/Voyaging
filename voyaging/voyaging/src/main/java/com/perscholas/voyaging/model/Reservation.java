package com.perscholas.voyaging.model;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    @ManyToOne
    @JoinColumn(name="customer_id")
    Customer customer;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "room_reservation",
            joinColumns = { @JoinColumn(name = "reservation_id") },
            inverseJoinColumns = { @JoinColumn(name = "room_id") }
    )
    Set<Room> rooms = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="package_id")
    Package reservationPackage;
    LocalDate checkinDate;
    LocalDate checkoutDate;

    public void addRoom(Room room) {
        rooms.add(room);
        room.getReservations().add(this);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
        room.getReservations().remove(this);
    }



}
