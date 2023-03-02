package com.perscholas.voyaging.service;

import com.perscholas.voyaging.dto.RoomDTO;
import com.perscholas.voyaging.model.Customer;
import com.perscholas.voyaging.model.Reservation;
import com.perscholas.voyaging.model.Room;
import com.perscholas.voyaging.model.RoomCategory;
import com.perscholas.voyaging.repository.CustomerRepository;
import com.perscholas.voyaging.repository.ReservationRepository;
import com.perscholas.voyaging.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
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

    public Reservation saveReservation(LocalDate checkin, LocalDate checkout, int nbGuests, Customer customer, RoomDTO roomDto) {
        Reservation reservation = new Reservation();
        reservation.setCheckinDate(checkin);
        reservation.setCheckoutDate(checkout);
        reservation.setNbGuests(nbGuests);

        Room room = roomRepository.findById( roomDto.getId()).get();

        //find room by category



        Reservation savedReservation = reservationRepository.save(reservation);
        customer.addReservation(reservation);
        room.addReservation(reservation);



       //find available rooms by category
        //reservation.setRooms(roomSet);

        return reservationRepository.save(reservation);

    }
}
