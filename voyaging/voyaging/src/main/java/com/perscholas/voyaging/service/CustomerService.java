package com.perscholas.voyaging.service;

import com.perscholas.voyaging.model.Address;
import com.perscholas.voyaging.model.CreditCard;
import com.perscholas.voyaging.model.Customer;
import com.perscholas.voyaging.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private  CustomerRepository customerRepository;



    public void saveCustomer(Customer customer, Address address, CreditCard creditCard) {

        customer.setAddress(address);

        if(creditCard.getMonthExpiration().length()==1){
            creditCard.setMonthExpiration("0"+creditCard.getMonthExpiration());
        }
        creditCard.setYearExpiration(creditCard.getYearExpiration().substring(2));

        customer.setCard(creditCard);

        customerRepository.save(customer);
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }
}
