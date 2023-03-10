package com.perscholas.voyaging.controller;

import com.perscholas.voyaging.model.Address;
import com.perscholas.voyaging.model.CreditCard;
import com.perscholas.voyaging.model.Customer;
import com.perscholas.voyaging.service.CustomerService;
import com.perscholas.voyaging.service.ReservationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class DashboardController {
    @Autowired
    ReservationService reservationService;

    @Autowired
    CustomerService customerService;
    @GetMapping
    public String dashboardView() {
        return "dashboard";
    }

    @GetMapping("/reservation")
    public String viewReservation(Model model){
        model.addAttribute("reservations", reservationService.findAllReservations());
        return "reservations";
    }

    @GetMapping("/customer")
    public String viewCustomers(Model model) {
        model.addAttribute("customers",customerService.findAllCustomers());
        return "customers";
    }
    @GetMapping("/customer/delete/{customerId:.+}")
    public String deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return "redirect:/dashboard/customer";
    }



}
