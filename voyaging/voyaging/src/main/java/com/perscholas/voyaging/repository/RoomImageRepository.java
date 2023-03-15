package com.perscholas.voyaging.repository;

import com.perscholas.voyaging.model.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {
    boolean existsByRoomTypeId(Long roomTypeId);
    RoomImage findByRoomTypeId(Long roomTypeId);

}
