package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(Long petId) {
        Customer customer;
        List<Customer> customers = customerRepository.findAllCustomerByPetsId(petId);
        if (customers.size()>1)
            customer = customers.get(0);
        else
            throw new CustomerNotFoundException("Customer not found");
        return customer;
    }
}
