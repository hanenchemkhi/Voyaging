package com.perscholas.voyaging.controller;

import com.perscholas.voyaging.dto.RoomDTO;
import com.perscholas.voyaging.model.*;
import com.perscholas.voyaging.repository.CustomerRepository;
import com.perscholas.voyaging.service.CustomerService;
import com.perscholas.voyaging.service.ReservationService;
import com.perscholas.voyaging.service.RoomService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.security.Principal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping()
public class ReservationController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/search-result")
    public String searchRooms(@RequestParam("checkinDate")@Valid @NotNull @Future(message = "Invalid checkin date") LocalDate checkinDate,
                              @RequestParam("checkoutDate")@Valid @NotNull @Future(message = "Invalid checkout date")LocalDate checkoutDate,
                              @RequestParam("nbRooms")@Valid @NotNull @Min(value = 1) @Max(value = 4) Integer numberRooms,
                              @RequestParam("nbGuests")@Valid @NotNull @Min(value = 1) @Max(value = 4) Integer numberGuests,
                              Model model ){


        List<Room> availableRooms = roomService.findAvailableRooms(checkinDate, checkoutDate);


        Set<RoomType> availableRoomType = availableRooms.stream()
                .map(room -> room.getRoomType())
                .filter(roomType -> roomType.getMaxGuests()>=numberGuests)
                .collect(Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()))
                // Convert this map into a stream
                .entrySet()
                .stream()
                // Check if frequency > numberRooms
                // for duplicate elements
                .filter(m -> m.getValue() >= numberRooms)
                // Find such elements
                .map(Map.Entry::getKey)
                // And Collect them in a Set
                .collect(Collectors.toSet());

        model.addAttribute("availableRooms", availableRoomType);
        model.addAttribute("checkin",checkinDate );
        model.addAttribute("checkout",checkoutDate );
        model.addAttribute("nbRooms", numberRooms);
        model.addAttribute("nbGuests", numberGuests);
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
    public String saveReservation(Model model, HttpSession httpSession, Principal principal){


        LocalDate checkin = LocalDate.parse(httpSession.getAttribute("checkin").toString());
        LocalDate checkout = LocalDate.parse(httpSession.getAttribute("checkout").toString());
        Long lengthOfStay = reservationService.findLengthOfStay(checkin, checkout);

        int nbGuests =(Integer) httpSession.getAttribute("nbGuests");
        int nbRooms = (Integer) httpSession.getAttribute("nbRooms");
        RoomType roomType = (RoomType)httpSession.getAttribute("roomType");

//        Customer customer = (Customer) httpSession.getAttribute("customer");

        log.warn(principal.getName());

        Customer customer = customerService.findCustomerByEmail(principal.getName());

        httpSession.setAttribute("customer", customer);

        Reservation reservation =  reservationService.saveReservation(checkin,checkout, nbGuests, customer, roomType,nbRooms);

        httpSession.setAttribute("reservation", reservation);


        return "redirect:/confirmation";
    }

    @GetMapping("/confirmation")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String confirmReservation(Model model, HttpSession httpSession){
        log.warn("inside confirmation");

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
