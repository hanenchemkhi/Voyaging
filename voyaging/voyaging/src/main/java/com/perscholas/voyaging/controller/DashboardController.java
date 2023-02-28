package com.perscholas.voyaging.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/dashboard")
public class DashboardController {
    @GetMapping
    public String dashboardView() {
        return "dashboard";
    }
}
