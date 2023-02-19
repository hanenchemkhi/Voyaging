package com.perscholas.voyaging.controller;

import com.perscholas.voyaging.model.Address;
import com.perscholas.voyaging.model.CreditCard;
import com.perscholas.voyaging.model.Customer;
import com.perscholas.voyaging.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping(value={"customer"})
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @GetMapping
    public String viewCustomers(Model model) {
        model.addAttribute("customers",customerService.findAllCustomers());
        return "customers";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer, @ModelAttribute("address")Address address,
                               @ModelAttribute("creditCard")CreditCard creditCard){

       customerService.saveCustomer(customer, address, creditCard);
        return "redirect:/customer";
    }

    @GetMapping("/delete/{customerId:.+}")
    public String deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return "redirect:/customer";
    }
}
