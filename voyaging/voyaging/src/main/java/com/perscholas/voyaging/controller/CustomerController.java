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
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/customer")

public class CustomerController {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    CustomerService customerService;

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

        if(!customer.getPassword().equals(customer.getConfirmPassword())){
            bindingResultCustomer.addError(new FieldError("customer","password", "Passwords don't match."));
            bindingResultCustomer.addError(new FieldError("customer","confirmPassword", "Passwords don't match."));
        }
        //Verifying if email already taken
        List<String> emails = customerService.findAllEmails();
        if(emails.contains(customer.getEmail())){
            bindingResultCustomer.addError(new FieldError("customer","email","Email is already in use."));
        }


        if(bindingResultCustomer.hasErrors() || bindingResultAddress.hasErrors() || bindingResultCreditCard.hasErrors()) {
            log.warn("======================errors with binding result=======================");
            return "signup";
        }


        log.warn("inside PostMapping /handlesignup");

        httpSession.setAttribute("customer", customer);
        httpSession.setAttribute("address", address);
        httpSession.setAttribute("creditCard", creditCard);
        String message = customerService.saveCustomer(customer, address, creditCard);
        httpSession.setAttribute("msg", message);
        return "redirect:/customer/save-reservation";
    }

    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer,
                               @Valid @ModelAttribute("address") Address address, BindingResult bindingResultAddress,
                               @Valid @ModelAttribute("creditCard") CreditCard creditCard, BindingResult bindingResultCreditCard){

        if( bindingResultAddress.hasErrors() || bindingResultCreditCard.hasErrors()) {
            log.warn("======================errors with binding result=======================");
            log.warn(bindingResultAddress.getAllErrors().toString());

            return "redirect:/dashboard/customer";
        }

        if(customerService.isCustomerExist(customer.getEmail())) {
            customerService.updateCustomer(customer, address, creditCard);
        }else{
            customerService.saveCustomer(customer, address, creditCard);
       }


        return "redirect:/dashboard/customer";
    }

}
