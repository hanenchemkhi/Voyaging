package com.perscholas.voyaging.controller;

import com.perscholas.voyaging.model.Address;
import com.perscholas.voyaging.model.CreditCard;
import com.perscholas.voyaging.model.Customer;
import com.perscholas.voyaging.repository.AddressRepository;
import com.perscholas.voyaging.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping(value={"customer"})
public class CustomerController {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    CustomerService customerService;
    @GetMapping
    public String viewCustomers(Model model) {
        model.addAttribute("customers",customerService.findAllCustomers());
        return "customers";
    }

    @PostMapping("/save")
    public String saveCustomer(@Valid @ModelAttribute("customer")  Customer customer, BindingResult bindingResultCustomer,
                               @Valid @ModelAttribute("address")  Address address, BindingResult bindingResultAddress,
                               @Valid @ModelAttribute("creditCard")  CreditCard creditCard, BindingResult bindingResultCreditCard){

        if(bindingResultCustomer.hasErrors() || bindingResultAddress.hasErrors() || bindingResultCreditCard.hasErrors()) {
            log.warn("======================errors with binding result=======================");



            return "redirect:/customer";
        }
        if(customerService.isCustomerExist(customer.getEmail())) {
            customerService.updateCustomer(customer, address, creditCard);
        }else{
            customerService.saveCustomer(customer, address, creditCard);
        }


        return "redirect:/customer";
    }


    @GetMapping("/signup")
    public String getSignup(Customer customer, Address address, CreditCard creditCard, Model model) {

        log.warn("inside getMapping /signup");
        model.addAttribute("customer", customer);
        model.addAttribute("address", address);
        model.addAttribute("creditCard", creditCard);

        return "signup";
    }

    @PostMapping("/handlesignup")
    public String signupCustomer(@Valid @ModelAttribute("customer")  Customer customer, BindingResult bindingResultCustomer,
                                 @Valid @ModelAttribute("address")  Address address, BindingResult bindingResultAddress,
                                 @Valid @ModelAttribute("creditCard")  CreditCard creditCard, BindingResult bindingResultCreditCard,
                                 HttpSession httpSession, Model model){

        if(bindingResultCustomer.hasErrors() || bindingResultAddress.hasErrors() || bindingResultCreditCard.hasErrors()) {
            log.warn("======================errors with binding result=======================");

            return "signup";
        }


        log.warn("inside PostMapping /signup");


        httpSession.setAttribute("customer", customer);
        httpSession.setAttribute("address", address);
        httpSession.setAttribute("creditCard", creditCard);
        customerService.saveCustomer(customer, address, creditCard);
        return "redirect:/confirmation";
    }



    @GetMapping("/delete/{customerId:.+}")
    public String deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return "redirect:/customer";
    }

}
