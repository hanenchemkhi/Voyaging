package com.perscholas.voyaging.controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;


@ControllerAdvice
public class AdviceController {
    @ExceptionHandler(Exception.class)
    public String exception(final Exception exception, final Model model) {

        String errorMessage = (exception != null ? exception.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

//    @ModelAttribute
//    public void bookingSelections (Model model, HttpServletRequest request, HttpSession http){
//        http.setAttribute("checkin", new Object());
//        http.setAttribute("checkout", new Object());
//        http.setAttribute("nbRooms",new Object());
//        http.setAttribute("nbGuests", new Object());
//
//
//
//        model.addAttribute("checkin", new Object() );
//        model.addAttribute("checkout", new Object());
//        model.addAttribute("nbRooms",new Object());
//        model.addAttribute("nbGuests", new Object());
//    }

}