package com.perscholas.voyaging.dto;

import com.perscholas.voyaging.model.Amenities;
import com.perscholas.voyaging.model.Reservation;
import com.perscholas.voyaging.model.RoomCategory;
import jakarta.annotation.Resource;
import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.support.ServletContextResource;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomDTO {
    Long id;
    Long roomNumber;
    RoomCategory roomCategory;
    Set<Amenities> amenities;
    Set<Reservation> reservations;
    Long price;
    Integer maxGuests;
    String imageData;
    String imageName;
}
