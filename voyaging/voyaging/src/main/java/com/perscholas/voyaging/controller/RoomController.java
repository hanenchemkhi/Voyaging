package com.perscholas.voyaging.controller;


import com.perscholas.voyaging.model.*;
import com.perscholas.voyaging.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.text.NumberFormat;

import java.util.List;
@Controller
@Slf4j
@RequestMapping("/dashboard/room")
public class RoomController {
    @Autowired
    RoomService roomService;
    @GetMapping
    public String viewRooms(Model model){
        List<Room> allRooms = roomService.findAllRooms();
        log.warn(allRooms.toString());
        List<RoomType> allRoomTypes = roomService.findAllRoomType();
        model.addAttribute("roomTypes", allRoomTypes);
        model.addAttribute("rooms", allRooms);
        model.addAttribute("roomCategories", roomService.findRoomCategories());

        return "rooms";
    }

    @PostMapping("/save/room")
    public String saveRoom(@RequestParam("roomNumber") Integer roomNumber, @RequestParam("category") RoomCategory roomCategory){

        roomService.saveRoom(roomNumber, roomCategory);
        log.warn("room :"+roomService.saveRoom(roomNumber, roomCategory).toString());
        return "redirect:/dashboard/room";
    }
    @PostMapping("/save/roomCategory")
    public String saveRoomCategory(@ModelAttribute("roomType")RoomType roomType,
                           @RequestParam("file") MultipartFile file ){
        roomService.saveRoomCategory(roomType,file);
        return "redirect:/dashboard/room";
    }


    @GetMapping("/delete/{roomId:.+}")
    public String deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return "redirect:/dashboard/room";
    }
    @GetMapping("roomType/delete/{roomTypeId:.+}")
    public String deleteRoomType(@PathVariable Long roomTypeId){
        roomService.deleteRoomType(roomTypeId);
        return "redirect:/dashboard/room";
    }

}