package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // saveCustomer: save or saveAll
    // getAllCustomers: findAll

    // getOwnerByPet
    List<Customer> findAllCustomerByPetsId(Long petId);
}
