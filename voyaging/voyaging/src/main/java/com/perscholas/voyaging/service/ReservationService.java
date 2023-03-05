package com.perscholas.voyaging.service;

import com.perscholas.voyaging.dto.RoomDTO;
import com.perscholas.voyaging.model.Customer;
import com.perscholas.voyaging.model.Reservation;
import com.perscholas.voyaging.model.Room;
import com.perscholas.voyaging.model.RoomCategory;
import com.perscholas.voyaging.repository.CustomerRepository;
import com.perscholas.voyaging.repository.ReservationRepository;
import com.perscholas.voyaging.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Slf4j
public class ReservationService {
    @Autowired
    private final ReservationRepository reservationRepository;
    @Autowired
    private final CustomerService customerService;
    private final RoomRepository roomRepository;


    public ReservationService(ReservationRepository reservationRepository,
                             CustomerService customerService,
                              RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;

        this.customerService = customerService;
        this.roomRepository = roomRepository;
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

    public Reservation saveReservation(LocalDate checkin, LocalDate checkout, int nbGuests, Customer customer, Room room) {


        Reservation reservation = new Reservation();

        reservation.setCheckinDate(checkin);
        log.warn("checkin date "+ checkin);
        reservation.setCheckoutDate(checkout);
        reservation.setNbGuests(nbGuests);
        log.warn("nb guests "+ nbGuests);



        //find room by category



        Reservation savedReservation = reservationRepository.save(reservation);
        log.warn("saved reservation");
        customer.addReservation(savedReservation);
        log.warn("added reservation to customer table");
        reservation.addRoom(room);

        log.warn("added reservation to room table");



       //find available rooms by category
        //reservation.setRooms(roomSet);

        return reservationRepository.save(reservation);

    }
}
