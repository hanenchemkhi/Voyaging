package com.perscholas.voyaging.repository;

import com.perscholas.voyaging.model.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {

    Optional<RoomImage> findByImageName(String imageName);
    Optional<RoomImage> findByImageUrl(String url);

}
