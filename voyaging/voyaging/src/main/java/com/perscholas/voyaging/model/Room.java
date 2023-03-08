package com.perscholas.voyaging.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
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

    @Column(unique = true)
    @NotNull(message="Please enter room number")
    Integer roomNumber;


    @NotNull(message="Please select room category")
    @ManyToOne
    @JoinColumn(name="room_type_id")
    RoomType roomType;


    public Room(Integer roomNumber, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
    }

    @ManyToMany(mappedBy = "rooms",fetch = FetchType.EAGER)
    Set<Reservation> reservations = new HashSet<>();

    @Override
    public String toString() {
        return  roomNumber+ " "+ roomType.getRoomCategory().getCategory();

    }
}
