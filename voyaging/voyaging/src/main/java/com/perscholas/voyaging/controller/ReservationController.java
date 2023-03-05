package com.perscholas.voyaging.controller;

import com.perscholas.voyaging.dto.RoomDTO;
import com.perscholas.voyaging.model.Customer;
import com.perscholas.voyaging.model.Reservation;
import com.perscholas.voyaging.model.Room;
import com.perscholas.voyaging.model.RoomCategory;
import com.perscholas.voyaging.service.ReservationService;
import com.perscholas.voyaging.service.RoomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequestMapping
@SessionAttributes(value={"checkinDate", "checkoutDate", "nbRooms", "nbGuests", "room", "customer"})
public class ReservationController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private ReservationService reservationService;


    @GetMapping("/search-result")
    public String searchRooms(@RequestParam("checkinDate") LocalDate checkinDate, @RequestParam("checkoutDate") LocalDate checkoutDate,
                              @RequestParam("nbRooms") int numberRooms, @RequestParam("nbGuests") int numberGuests, Model model, HttpSession httpSession ){
        List<Room> availableRooms = roomService.findAvailableRooms(checkinDate, checkoutDate, numberRooms,numberGuests);

        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("checkin",checkinDate );
        model.addAttribute("checkout",checkoutDate );
        model.addAttribute("nbRooms", numberRooms);
        model.addAttribute("nbGuests", numberGuests);
        model.addAttribute("reservationService", reservationService);




        return "search-result";
    }


    @GetMapping("/book")
    public String viewBooking(@RequestParam("id") Long id,
                              @RequestParam("checkin") LocalDate checkin,
                              @RequestParam("checkout") LocalDate checkout,
                              @RequestParam("nbRooms") int nbRooms,
                              @RequestParam("nbGuests") int nbGuests,
                              Model model, HttpSession httpSession){

        Long lengthOfStay = reservationService.findLengthOfStay(checkin, checkout);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        Room room = roomService.finRoomById(id);



        model.addAttribute("lengthOfStay" , lengthOfStay);
        model.addAttribute("room", room );
        model.addAttribute("checkin",reservationService.formatDate( checkin) );
        model.addAttribute("checkout", reservationService.formatDate(checkout) );
        model.addAttribute("nbRoooms", nbRooms);
        model.addAttribute("nbGuests", nbGuests);
        model.addAttribute("price", formatter.format(room.getRoomType().getPrice()));
        model.addAttribute("taxes", formatter.format(roomService.calculateTaxes(id)));
        model.addAttribute("costPerRoom", formatter.format(roomService.calculateCostPerRoom(id, nbRooms)));
        model.addAttribute("totalCost", formatter.format(roomService.calculateTotalCost(id, lengthOfStay, nbRooms)));

        httpSession.setAttribute("checkin",checkin );
        httpSession.setAttribute("checkout", checkout );
        httpSession.setAttribute("nbRooms", nbRooms);
        httpSession.setAttribute("nbGuests", nbGuests);
        httpSession.setAttribute("room", room );

        return "book";
    }


    @GetMapping("/reservation")
    public String viewReservation(){
        return "reservations";
    }

    @GetMapping("/confirmation")
    public String confirmationReservation(Model model, HttpSession httpSession){
        log.warn(httpSession.getAttribute("checkin").toString());
        log.warn(httpSession.getAttribute("checkout").toString());
        //save reservation and send confirmation

        LocalDate checkin = LocalDate.parse(httpSession.getAttribute("checkin").toString());
        LocalDate checkout = LocalDate.parse(httpSession.getAttribute("checkout").toString());
        int nbGuests =(Integer) httpSession.getAttribute("nbGuests");
        Customer customer = (Customer) httpSession.getAttribute("customer");

        Room room = (Room)httpSession.getAttribute("room");


       Reservation reservation =  reservationService.saveReservation(checkin,checkout, nbGuests, customer, room);

        model.addAttribute("reservation", reservation);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("customer", customer);
        model.addAttribute("room",room);

        return "confirmation";
    }



}
