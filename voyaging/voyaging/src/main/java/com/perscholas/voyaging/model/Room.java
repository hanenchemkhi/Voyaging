package com.perscholas.voyaging.model;


import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


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
    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL)
    RoomType roomType;
    @ManyToMany(mappedBy = "rooms")
    Set<Reservation> reservations;
    @ManyToMany(mappedBy = "rooms")
    List<RoomPrice> roomPrices= new LinkedList<>();
    Integer maxGuests;
    String picture;


}
