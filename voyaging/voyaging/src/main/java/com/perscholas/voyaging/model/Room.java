package com.perscholas.voyaging.model;


import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    Long roomNumber;

    @Enumerated(EnumType.STRING)
    RoomCategory roomCategory;
    @ElementCollection(targetClass =Amenities.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<Amenities> amenities ;
    @ManyToMany(mappedBy = "rooms")
    Set<Reservation> reservations = new HashSet<>();
//    @ManyToMany(mappedBy = "rooms")
//    Set<RoomPrice> roomPrices= new HashSet<>();
    Long price;
    Integer maxGuests;
    String photo;

}
