package com.perscholas.voyaging.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {
    @GetMapping("/login")
    public String viewLogin(){
        return "login";
    }
    @GetMapping("/signup")
    public String viewSignup(){
        return "signup";
    }
}
