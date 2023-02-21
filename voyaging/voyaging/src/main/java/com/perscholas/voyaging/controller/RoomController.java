package com.perscholas.voyaging.controller;

import com.perscholas.voyaging.model.Room;
import com.perscholas.voyaging.repository.CreditCardRepository;
import com.perscholas.voyaging.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


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
        model.addAttribute("rooms", roomService.findAllRooms());
        return "rooms";
    }

    @PostMapping("/save")
    public String saveRoom(@ModelAttribute("room") Room room, @RequestParam("image")MultipartFile image){
        roomService.saveRoom(room, image);
        return "redirect:/room";
    }

}