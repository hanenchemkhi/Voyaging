package com.perscholas.voyaging.service;

import com.perscholas.voyaging.model.*;
import com.perscholas.voyaging.repository.ReservationRepository;
import com.perscholas.voyaging.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Slf4j
public class ReservationService {
    @Autowired
    private final ReservationRepository reservationRepository;
    @Autowired
    private final CustomerService customerService;
    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final RoomService roomService;



    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              CustomerService customerService,
                              RoomRepository roomRepository, RoomService roomService) {
        this.reservationRepository = reservationRepository;

        this.customerService = customerService;
        this.roomRepository = roomRepository;
        this.roomService = roomService;
    }

    public Long findLengthOfStay(LocalDate checkin, LocalDate checkout) {
        return DAYS.between(checkin, checkout);
    }

    public String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));

    }

    public String formatPrice(Double price){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(price);
    }

    public Reservation saveReservation(LocalDate checkin, LocalDate checkout, int nbGuests, Customer customer, RoomType roomType, int nbRooms) {

        List<Room> rooms = roomService.findAvailableRooms(checkin,checkout)
                .stream()
                .filter(room -> room.getRoomType().equals(roomType))
                .collect(Collectors.toList());
        Reservation reservation = new Reservation();

        for (int i = 0; i <nbRooms ; i++) {

            reservation.setCheckinDate(checkin);
            reservation.setCheckoutDate(checkout);
            reservation.setNbGuests(nbGuests);

            Reservation savedReservation = reservationRepository.save(reservation);

            customer.addReservation(savedReservation);
            reservation.addRoom(rooms.get(i));
            reservationRepository.save(reservation);

        }
        return reservation;
    }

    public Object findAllReservations() {

        return reservationRepository.findAll();
    }
}
