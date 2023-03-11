package com.perscholas.voyaging.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping(value={"", "/", "index"})
public class HomeController {

    @GetMapping()
    public String indexView(Model model) {

        model.addAttribute("checkinDate", LocalDate.now());
        model.addAttribute("checkoutDate", LocalDate.now().plusDays(1));
        model.addAttribute("nbRooms",1);
        model.addAttribute("nbGuests",1);

        return "index";
    }

}
