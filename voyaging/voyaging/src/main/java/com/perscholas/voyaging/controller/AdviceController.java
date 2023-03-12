package com.perscholas.voyaging.controller;
import com.perscholas.voyaging.model.User;
import com.perscholas.voyaging.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.time.LocalDate;


@ControllerAdvice
@Slf4j
public class AdviceController {
    private final UserRepository userRepository;

    public AdviceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ExceptionHandler(Exception.class)
   public String exception(final Exception exception, final Model model) {

      String errorMessage = (exception != null ? exception.getMessage() : "Unknown error");
       return "error";
   }

//    @ModelAttribute
//    public void theStudent(Model model, HttpServletRequest request, HttpSession http){
//        Principal principal = request.getUserPrincipal();
//        User user = null;
//        if(principal != null){
//            user =  userRepository.findByEmail(principal.getName()).get();
//            http.setAttribute("user", user);
//            log.warn("session attribute in advice controller  " + http.getAttribute("user").toString());
//
//        }
//
//    }

}