package com.perscholas.voyaging.controller;

import com.perscholas.voyaging.dto.RoomDTO;
import com.perscholas.voyaging.model.*;
import com.perscholas.voyaging.service.RoomService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@Slf4j
@RequestMapping("room")
public class RoomController {
    @Autowired
    RoomService roomService;
    @GetMapping
    public String viewRooms(Model model){
        List<Room> allRooms = roomService.findAllRooms();


        List<RoomType> allRoomTypes = roomService.findAllRoomType();
        model.addAttribute("roomTypes", allRoomTypes);
        model.addAttribute("rooms", allRooms);

        return "rooms";
    }

    @PostMapping("/save/room")
    public String saveRoom(@RequestParam("roomNumber") Integer roomNumber, @RequestParam("category") RoomCategory roomCategory){
        roomService.saveRoom(roomNumber, roomCategory);
        return "redirect:/room";
    }
    @PostMapping("/save/roomCategory")
    public String saveRoomCategory(@ModelAttribute("roomType")RoomType roomType,
                           @RequestParam("file") MultipartFile file ){
        roomService.saveRoomCategory(roomType,file);
        return "redirect:/room";
    }


    @GetMapping("/delete/{roomId:.+}")
    public String deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return "redirect:/room";
    }

}