package com.perscholas.voyaging.controller;

import com.perscholas.voyaging.model.Room;
import com.perscholas.voyaging.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequestMapping
public class ReservationController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/book")
    public void viewBooking(){

    }
    @GetMapping("/search-result")
    public String searchRooms(@RequestParam("checkinDate") LocalDate checkinDate, @RequestParam("checkoutDate") LocalDate checkoutDate,
                              @RequestParam("numberRooms") int numberRooms, @RequestParam("numberGuests") int numberGuests, Model model ){
        List<Room> availableRooms = roomService.findAvailableRooms(checkinDate, checkoutDate, numberRooms,numberGuests);
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("checkin",checkinDate );
        model.addAttribute("checkout",checkoutDate );
        return "search-result";
    }







}
