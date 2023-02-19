package com.perscholas.voyaging.service;

import com.perscholas.voyaging.model.Reservation;
import com.perscholas.voyaging.model.Room;
import com.perscholas.voyaging.repository.ReservationRepository;
import com.perscholas.voyaging.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class RoomService {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public RoomService(RoomRepository roomRepository,
                       ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Room> findAvailableRooms(LocalDate checkinDate, LocalDate checkoutDate, int numberRooms, int numberGuests) {
        List<Room> availableRooms = roomRepository.findAll();
        List<Reservation> allReservations = reservationRepository.findAll();
        List<Room> reservedRooms = new ArrayList<>();

        for (Reservation reservation : allReservations) {

            if ( checkinDate.isBefore(reservation.getCheckinDate())
                    && checkoutDate.isAfter(reservation.getCheckinDate())) {
                reservedRooms.addAll(reservation.getRooms());
            }
        }
        availableRooms.removeAll(reservedRooms);

        return availableRooms;

    }
}
