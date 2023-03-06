package com.perscholas.voyaging.controller;

import com.perscholas.voyaging.dto.RoomDTO;
import com.perscholas.voyaging.model.*;
import com.perscholas.voyaging.service.ReservationService;
import com.perscholas.voyaging.service.RoomService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping
@Validated
public class ReservationController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private ReservationService reservationService;


    @GetMapping("/search-result")
    public String searchRooms(@RequestParam("checkinDate") @Future LocalDate checkinDate,BindingResult bindingResultCheckin,
                              @RequestParam("checkoutDate") @Future LocalDate checkoutDate,BindingResult bindingResultCheckout,
                              @RequestParam("nbRooms") @Min(value = 1) @Max(value = 4)int numberRooms,BindingResult bindingResultNbRooms,
                              @RequestParam("nbGuests")@Min(value = 1) @Max(value = 4) int numberGuests,BindingResult bindingResultNbGuests,
                              Model model, HttpSession httpSession ){


        if(bindingResultCheckin.hasErrors()|| bindingResultCheckout.hasErrors()||
                bindingResultNbRooms.hasErrors()||bindingResultNbGuests.hasErrors() ){

            return "index";
        }

        List<Room> availableRooms = roomService.findAvailableRooms(checkinDate, checkoutDate);


        Set<RoomType> availableRoomType = availableRooms.stream()
                .map(room -> room.getRoomType())
                .collect(Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()))
                // Convert this map into a stream
                .entrySet()
                .stream()
                // Check if frequency > numberRooms
                // for duplicate elements
                .filter(m -> m.getValue() >= numberRooms)
                // Find such elements
                .map(Map.Entry::getKey)
                // And Collect them in a Set
                .collect(Collectors.toSet());





        model.addAttribute("availableRooms", availableRoomType);

        //model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("checkin",checkinDate );
        model.addAttribute("checkout",checkoutDate );
        model.addAttribute("nbRooms", numberRooms);
        model.addAttribute("nbGuests", numberGuests);
        model.addAttribute("reservationService", reservationService);

        return "search-result";
    }


    @GetMapping("/book")
    public String viewBooking(@RequestParam("roomTypeId") Long id,
                              @RequestParam("checkin") LocalDate checkin,
                              @RequestParam("checkout") LocalDate checkout,
                              @RequestParam("nbRooms") int nbRooms,
                              @RequestParam("nbGuests") int nbGuests,
                              Model model, HttpSession httpSession){

        Long lengthOfStay = reservationService.findLengthOfStay(checkin, checkout);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        //find available rooms with roomType selected by user
//        List<RoomType> roomType = roomService.findAllRoomType();
        Room room = roomService.findRoomByRoomType(id);


        model.addAttribute("lengthOfStay" , lengthOfStay);
        model.addAttribute("room", room );
        model.addAttribute("checkin",reservationService.formatDate( checkin) );
        model.addAttribute("checkout", reservationService.formatDate(checkout) );
        model.addAttribute("nbRooms", nbRooms);
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
