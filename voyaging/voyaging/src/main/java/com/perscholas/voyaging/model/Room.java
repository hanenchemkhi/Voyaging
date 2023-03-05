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

    @NotNull(message="Please enter room number")
    Long roomNumber;


    @NotNull(message="Please select room category")
    @ManyToOne
    @JoinColumn(name="room_type_id")
    RoomType roomType;



    @ManyToMany(mappedBy = "rooms",fetch = FetchType.EAGER)
    Set<Reservation> reservations = new HashSet<>();


}
