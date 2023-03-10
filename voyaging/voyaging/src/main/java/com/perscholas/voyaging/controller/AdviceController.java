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
       return "error";
   }

}