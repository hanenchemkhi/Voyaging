package com.perscholas.voyaging.controller;

import com.perscholas.voyaging.dto.RoomDTO;
import com.perscholas.voyaging.model.Room;
import com.perscholas.voyaging.service.ReservationService;
import com.perscholas.voyaging.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
                              @RequestParam("nbRooms") int numberRooms, @RequestParam("nbGuests") int numberGuests, Model model ){
        List<RoomDTO> availableRooms = roomService.findAvailableRooms(checkinDate, checkoutDate, numberRooms,numberGuests);

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
                              Model model){

        Long lengthOfStay = reservationService.findLengthOfStay(checkin, checkout);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        RoomDTO room = roomService.findRoomDTOById(id);


        model.addAttribute("lengthOfStay" , lengthOfStay);
        model.addAttribute("room", room );

        model.addAttribute("checkin",reservationService.formatDate( checkin) );
        model.addAttribute("checkout", reservationService.formatDate(checkout) );
        model.addAttribute("nbRoooms", nbRooms);
        model.addAttribute("nbGuests", nbGuests);
        model.addAttribute("price", formatter.format(room.getPrice()));
        model.addAttribute("taxes", formatter.format(roomService.calculateTaxes(id)));
        model.addAttribute("costPerRoom", formatter.format(roomService.calculateCostPerRoom(id, nbRooms)));
        model.addAttribute("totalCost", formatter.format(roomService.calculateTotalCost(id, lengthOfStay, nbRooms)));


        return "book";
    }


    @GetMapping("/reservation")
    public String viewReservation(){
        return "reservations";
    }

    @GetMapping("/confirmation")
    public String confirmationReservation(Model model){
        return "confirmation";
    }



}
