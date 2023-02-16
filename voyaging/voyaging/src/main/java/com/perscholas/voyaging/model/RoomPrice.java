package com.perscholas.voyaging.model;


import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;


import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class RoomPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "price_room",
            joinColumns = {@JoinColumn(name = "price_id" )},
            inverseJoinColumns = @JoinColumn(name ="room_id" ))
    List<Room> rooms = new LinkedList<>();

    LocalDate date;
    Long price;

}
