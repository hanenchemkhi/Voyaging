package com.perscholas.voyaging.repository;

import com.perscholas.voyaging.model.Room;
import com.perscholas.voyaging.model.RoomCategory;
import com.perscholas.voyaging.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findRoomsByRoomType(RoomType roomType);

    boolean existsRoomByRoomNumber(Integer roomNumber);

    Room findRoomByRoomNumber(Integer roomNumber);



}
