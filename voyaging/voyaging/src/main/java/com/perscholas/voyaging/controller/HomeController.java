package com.perscholas.voyaging.controller;

import com.perscholas.voyaging.dto.SearchCriteria;
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

        System.out.println(LocalDate.now());
        System.out.println(LocalDate.now().plusDays(1));
        model.addAttribute("searchCriteria", new SearchCriteria(LocalDate.now(),LocalDate.now().plusDays(1),1,1));

        return "index";
    }

}
