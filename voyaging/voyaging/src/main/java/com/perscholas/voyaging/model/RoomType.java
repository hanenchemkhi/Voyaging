package com.perscholas.voyaging.model;
import jakarta.persistence.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
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
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    @NotNull(message="Please enter max room occupancy")
    @Max(value= 6, message=("Maximum occupancy is 6"))
    int maxGuests;
    @NotNull(message="Please enter price")
    Double price;

    @NotNull(message="Please select room category")
    @Column(unique=true)
    @Enumerated(EnumType.STRING)
    RoomCategory roomCategory;
    @ToString.Exclude
    @OneToOne(mappedBy = "roomType", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, optional = true)
    RoomImage roomImage;

    @OneToMany(
            mappedBy = "roomType",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    Set<Room> rooms = new HashSet<>();


   @ElementCollection(targetClass =Amenities.class, fetch = FetchType.EAGER)
   @Enumerated(EnumType.STRING)
   Set<Amenities> amenities;

    public RoomType(int maxGuests, Double price, RoomCategory roomCategory) {
        this.maxGuests = maxGuests;
        this.price = price;
        this.roomCategory = roomCategory;
    }

    public void addRoom(Room room){
        rooms.add(room);
        room.setRoomType(this);
    }


    public void removeRoom(Room room) {
        rooms.remove(room);
        room.setRoomType(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomType roomType)) return false;

        if (!getPrice().equals(roomType.getPrice())) return false;
        return getRoomCategory() == roomType.getRoomCategory();
    }

    @Override
    public int hashCode() {
        int result = getPrice().hashCode();
        result = 31 * result + getRoomCategory().hashCode();
        return result;
    }
}
