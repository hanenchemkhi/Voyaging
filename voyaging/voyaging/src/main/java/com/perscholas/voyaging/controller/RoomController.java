package com.perscholas.voyaging.controller;

import com.perscholas.voyaging.model.Amenities;
import com.perscholas.voyaging.model.Room;
import com.perscholas.voyaging.repository.CreditCardRepository;
import com.perscholas.voyaging.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@Slf4j
@RequestMapping("room")
public class RoomController {
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    RoomService roomService;
    @GetMapping
    public String viewRooms(Model model){

        return "rooms";

    }
    @PostMapping("/save")
    public String saveRoom(@ModelAttribute("room") Room room, @ModelAttribute("amenities") Amenities amenities){
        
        roomService.saveRoom(room);
        return "redirect:/room";
    }


}