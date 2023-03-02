package com.perscholas.voyaging.model;


import jakarta.annotation.Resource;
import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;


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
    Double price;
    Integer maxGuests;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    byte[] imageData;
    String imageName;
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.getRooms().add(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.getRooms().remove(this);
    }
}
