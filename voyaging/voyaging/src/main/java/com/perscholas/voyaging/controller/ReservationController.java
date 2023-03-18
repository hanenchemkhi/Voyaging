package com.perscholas.voyaging.controller;


import com.perscholas.voyaging.dto.SearchCriteria;

import com.perscholas.voyaging.model.*;

import com.perscholas.voyaging.service.CustomerService;
import com.perscholas.voyaging.service.EmailSenderService;
import com.perscholas.voyaging.service.ReservationService;
import com.perscholas.voyaging.service.RoomService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;



import java.security.Principal;
import java.text.NumberFormat;
import java.time.LocalDate;





@Controller
@Slf4j
@RequestMapping()
public class ReservationController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ReservationService reservationService;

    @Autowired
    EmailSenderService emailSenderService;
    @GetMapping("/search-result")

    public String searchRooms(@Valid @ModelAttribute("searchCriteria") SearchCriteria searchCriteria,
                              BindingResult bindingResult,
                              Model model ){
        log.warn("Inside /search-result");
        log.warn("chekin date = "+searchCriteria.checkin());
        log.warn("chekin date = "+searchCriteria.checkout());

        if(bindingResult.hasErrors()){
            log.warn("Error was captured");
            log.warn("nbRooms = "+searchCriteria.nbRooms());
            if(searchCriteria.checkin()!=null && searchCriteria.checkout()!=null) {
                if (!searchCriteria.checkin().isBefore(searchCriteria.checkout())) {
                    bindingResult.addError(new FieldError("searchCriteria", "checkout", "Check-out date must be after check-in date."));
                    log.warn("Error was added to bindingResult");
                }
            }
            return "index";
        }

        model.addAttribute("availableRooms", roomService.availableRoomType(searchCriteria.checkin(),
                searchCriteria.checkout(),searchCriteria.nbRooms(),searchCriteria.nbGuests()));
        model.addAttribute("checkin",searchCriteria.checkin() );
        model.addAttribute("checkout",searchCriteria.checkout() );
        model.addAttribute("nbRooms", searchCriteria.nbRooms());
        model.addAttribute("nbGuests", searchCriteria.nbGuests());

        model.addAttribute("reservationService", reservationService);

        return "search-result";
    }


    @GetMapping("/book")
    public String viewBooking(@RequestParam("roomTypeId") Long id,
                              @RequestParam("checkin") LocalDate checkin,
                              @RequestParam("checkout") LocalDate checkout,
                              @RequestParam("nbRooms") int nbRooms,
                              @RequestParam("nbGuests") int nbGuests,
                              Model model, HttpSession httpSession){

        Long lengthOfStay = reservationService.findLengthOfStay(checkin, checkout);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        RoomType roomType = roomService.findRoomTypeById(id);

        model.addAttribute("lengthOfStay" , lengthOfStay);
        model.addAttribute("roomType", roomType );
        model.addAttribute("checkin",reservationService.formatDate( checkin) );
        model.addAttribute("checkout", reservationService.formatDate(checkout) );
        model.addAttribute("nbRooms", nbRooms);
        model.addAttribute("nbGuests", nbGuests);
        model.addAttribute("price", formatter.format(roomType.getPrice()));
        model.addAttribute("taxes", formatter.format(roomService.calculateTaxes(id)));
        model.addAttribute("costPerRoom", formatter.format(roomService.calculateCostPerRoom(id, nbRooms)));
        model.addAttribute("totalCost", formatter.format(roomService.calculateTotalCost(id, lengthOfStay, nbRooms)));

        httpSession.setAttribute("checkin",checkin );
        httpSession.setAttribute("checkout", checkout );
        httpSession.setAttribute("nbRooms", nbRooms);
        httpSession.setAttribute("nbGuests", nbGuests);
        httpSession.setAttribute("roomType", roomType );

        return "book";
    }


    @GetMapping("/customer/save-reservation")
    public String saveReservation(Model model, HttpSession httpSession, Principal principal) throws MessagingException {


        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        LocalDate checkin = LocalDate.parse(httpSession.getAttribute("checkin").toString());
        LocalDate checkout = LocalDate.parse(httpSession.getAttribute("checkout").toString());
        Long lengthOfStay = reservationService.findLengthOfStay(checkin, checkout);

        int nbGuests =(Integer) httpSession.getAttribute("nbGuests");
        int nbRooms = (Integer) httpSession.getAttribute("nbRooms");
        RoomType roomType = (RoomType)httpSession.getAttribute("roomType");



        log.warn(principal.getName());

        Customer customer = customerService.findCustomerByEmail(principal.getName());

        httpSession.setAttribute("customer", customer);

        Reservation reservation =  reservationService.saveReservation(checkin,checkout, nbGuests, customer, roomType,nbRooms);

        httpSession.setAttribute("reservation", reservation);


        emailSenderService.sendHtmlMessage(customer.getEmail(), customer, reservation,checkin,checkout,nbRooms,roomType,
                formatter.format(roomType.getPrice()),formatter.format(roomService.calculateTaxes(roomType.getId())),
                formatter.format(roomService.calculateCostPerRoom(roomType.getId(), nbRooms)),
                formatter.format(roomService.calculateTotalCost(roomType.getId(),lengthOfStay, nbRooms)));



        return "redirect:/customer/confirmation";
    }

    @GetMapping("/customer/confirmation")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String confirmReservation(Model model, HttpSession httpSession) throws MessagingException {


        NumberFormat formatter = NumberFormat.getCurrencyInstance();


        LocalDate checkin = LocalDate.parse(httpSession.getAttribute("checkin").toString());
        LocalDate checkout = LocalDate.parse(httpSession.getAttribute("checkout").toString());
        Long lengthOfStay = reservationService.findLengthOfStay(checkin, checkout);

        int nbGuests =(Integer) httpSession.getAttribute("nbGuests");
        int nbRooms = (Integer) httpSession.getAttribute("nbRooms");
        Customer customer = (Customer) httpSession.getAttribute("customer");

        RoomType roomType = (RoomType)httpSession.getAttribute("roomType");

        Reservation reservation =(Reservation)httpSession.getAttribute("reservation");

        model.addAttribute("reservation", reservation);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("customer", customer);
        model.addAttribute("nbRooms", nbRooms);
        model.addAttribute("roomType",roomType);
        model.addAttribute("price", formatter.format(roomType.getPrice()));
        model.addAttribute("taxes", formatter.format(roomService.calculateTaxes(roomType.getId())));
        model.addAttribute("costPerRoom", formatter.format(roomService.calculateCostPerRoom(roomType.getId(), nbRooms)));
        model.addAttribute("totalCost", formatter.format(roomService.calculateTotalCost(roomType.getId(), lengthOfStay, nbRooms)));

        return "confirmation";

    }
}
