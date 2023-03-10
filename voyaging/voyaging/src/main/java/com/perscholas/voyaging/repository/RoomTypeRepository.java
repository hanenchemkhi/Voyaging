package com.perscholas.voyaging.repository;

import com.perscholas.voyaging.model.RoomCategory;
import com.perscholas.voyaging.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    RoomType findByRoomCategory(RoomCategory roomCategory);
    boolean existsRoomTypesByRoomCategory(RoomCategory roomCategory);

}
