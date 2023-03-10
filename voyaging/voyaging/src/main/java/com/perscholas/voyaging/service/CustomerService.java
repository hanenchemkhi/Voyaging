package com.perscholas.voyaging.service;

import com.perscholas.voyaging.exception.CustomerNotFoundException;
import com.perscholas.voyaging.model.*;
import com.perscholas.voyaging.repository.AuthorityRepository;
import com.perscholas.voyaging.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    private  CustomerRepository customerRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    public String saveCustomer(Customer customer, Address address, CreditCard creditCard) {

        log.warn("inside saveCustomer");

        if(customerRepository.existsByEmail(customer.getEmail())){
            log.warn("An account with this email already exists");
            return "An account with this email already exists ";
        }

        log.warn("authority "+ authorityRepository.getReferenceById(2).toString());
        customer.addAuthority(authorityRepository.getReferenceById(2));

        customer.setPassword( new BCryptPasswordEncoder().encode(customer.getPassword()));
        customer.setRole(Role.CUSTOMER);
        log.warn("added auth to customer");

        customer.setAddress(address);

        if(creditCard.getMonthExpiration().length()==1){
            creditCard.setMonthExpiration("0"+creditCard.getMonthExpiration());
        }
        creditCard.setYearExpiration(creditCard.getYearExpiration().substring(2));

        customer.setCard(creditCard);
        customerRepository.saveAndFlush(customer);

        log.warn("Account successfully created");
        return "Account successfully created";
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }


    public Customer findCustomer(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return unwrapCustomer(customer, customerId);
    }
    static Customer unwrapCustomer(Optional<Customer> customer, Long id) {
        if (customer.isPresent()) return customer.get();
        else throw new CustomerNotFoundException(id);
    }

    public boolean isCustomerExist(String email) {
        return customerRepository.findCustomerByEmail(email).isPresent()?true:false;
    }

    public Customer updateCustomer(Customer customer, Address address, CreditCard creditCard) {


        Customer customerToUpdate = customerRepository.findCustomerByEmail(customer.getEmail()).get();

        customerToUpdate.setRole(Role.CUSTOMER);

        customerToUpdate.setFirstName(customer.getFirstName());
        customerToUpdate.setLastName(customer.getLastName());
        customerToUpdate.setPhone(customer.getPhone());
        customerToUpdate.setConfirmPassword(new BCryptPasswordEncoder().encode(customer.getConfirmPassword()));
        customerToUpdate.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));

        if(creditCard.getMonthExpiration().length()==1){
            creditCard.setMonthExpiration("0"+creditCard.getMonthExpiration());
        }
        creditCard.setYearExpiration(creditCard.getYearExpiration().substring(2));

        customerToUpdate.setCard(creditCard);
        customerToUpdate.setAddress(address);
        return customerRepository.save(customerToUpdate);


    }

    public Customer findCustomerByEmail(String email) {
            return customerRepository.findCustomerByEmail(email).get();
    }
}
