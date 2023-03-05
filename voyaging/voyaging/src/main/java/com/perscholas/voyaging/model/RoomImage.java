package com.perscholas.voyaging.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String imageName;

    String imageUrl;


    @ToString.Exclude
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_type_id")
    RoomType roomType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomImage image = (RoomImage) o;
        return Objects.equals(id, image.id) && imageName.equals(image.imageName) && imageUrl.equals(image.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageName, imageUrl);
    }
}
