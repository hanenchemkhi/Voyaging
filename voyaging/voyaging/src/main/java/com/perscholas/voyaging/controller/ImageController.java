package com.perscholas.voyaging.controller;

import com.perscholas.voyaging.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/image")
public class ImageController{

    @Autowired
    RoomService roomService;
    @GetMapping("/{filename:.+}")

    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Resource file = roomService.load(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+filename).body(file);
    }

}
