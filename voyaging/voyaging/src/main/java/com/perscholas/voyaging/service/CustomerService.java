package com.perscholas.voyaging.service;

import com.perscholas.voyaging.exception.CustomerNotFoundException;
import com.perscholas.voyaging.exception.RoomNotFoundException;
import com.perscholas.voyaging.model.Address;
import com.perscholas.voyaging.model.CreditCard;
import com.perscholas.voyaging.model.Customer;
import com.perscholas.voyaging.model.Room;
import com.perscholas.voyaging.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public Customer findCustomer(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return unwrapCustomer(customer, customerId);
    }
    static Customer unwrapCustomer(Optional<Customer> customer, Long id) {
        if (customer.isPresent()) return customer.get();
        else throw new CustomerNotFoundException(id);
    }

}
