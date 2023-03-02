package com.perscholas.voyaging.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequestMapping
public class LoginController {
    @GetMapping("/login")
    public String viewLogin(HttpSession httpSession){
        log.warn(httpSession.getAttribute("checkin").toString());

        return "login";
    }

    @GetMapping("/signup")
    public String viewSignup(){
        return "signup";
    }

    @GetMapping("/nesibe")
    public String viewNesibe(){
        return "nesibe";
    }


}
