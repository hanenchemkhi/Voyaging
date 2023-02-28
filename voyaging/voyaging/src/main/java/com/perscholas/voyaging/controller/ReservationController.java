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


import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequestMapping
@SessionAttributes()
public class ReservationController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/book")
    public String viewBooking(@RequestParam("id") Long id,
                              @RequestParam("checkin") LocalDate checkin,
                              @RequestParam("checkout") LocalDate checkout,
                              Model model){

        model.addAttribute("lengthOfStay" , reservationService.findLengthOfStay(checkin, checkout));
        model.addAttribute("room", roomService.findRoomDTOById(id));
        model.addAttribute("checkin",reservationService.formatDate( checkin) );
        model.addAttribute("checkout", reservationService.formatDate(checkout) );

        return "book";
    }



    @GetMapping("/reservation")
    public String viewReservation(){
        return "reservations";
    }




    @GetMapping("/search-result")
    public String searchRooms(@RequestParam("checkinDate") LocalDate checkinDate, @RequestParam("checkoutDate") LocalDate checkoutDate,
                              @RequestParam("numberRooms") int numberRooms, @RequestParam("numberGuests") int numberGuests, Model model ){
        List<RoomDTO> availableRooms = roomService.findAvailableRooms(checkinDate, checkoutDate, numberRooms,numberGuests);

        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("checkin",checkinDate );
        model.addAttribute("checkout",checkoutDate );
        return "search-result";
    }


    @GetMapping("/confirmation")
    public String confirmationReservation(Model model){




        return "confirmation";
    }







}
