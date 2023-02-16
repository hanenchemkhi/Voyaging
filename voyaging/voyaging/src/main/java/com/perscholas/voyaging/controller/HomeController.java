package com.perscholas.voyaging.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping(value={"","/","/index"})
    public String displayIndexPage(){
        return "index";
    }
}
