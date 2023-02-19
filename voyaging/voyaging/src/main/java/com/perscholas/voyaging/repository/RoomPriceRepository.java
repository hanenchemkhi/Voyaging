package com.perscholas.voyaging.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perscholas.voyaging.model.RoomPrice;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomPriceRepository extends JpaRepository<RoomPrice,Long> {
}
