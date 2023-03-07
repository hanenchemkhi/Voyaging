package com.perscholas.voyaging.model;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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


    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name="customer_id",referencedColumnName = "id", nullable = true)
    Customer customer;



    @ManyToMany(cascade = { CascadeType.ALL },fetch = FetchType.LAZY)
    @JoinTable(
            name = "room_reservation",
            joinColumns = { @JoinColumn(name = "reservation_id") },
            inverseJoinColumns = { @JoinColumn(name = "room_id") }
    )
    Set<Room> rooms = new HashSet<>();
    @NotNull
    LocalDate checkinDate;
    @NotNull
    LocalDate checkoutDate;

    @NotNull
    Integer nbGuests;

    public void addRoom(Room room) {
        rooms.add(room);
        room.getReservations().add(this);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
        room.getReservations().remove(this);
    }


    @Override
    public String toString() {
        return " " + rooms ;
    }
}
