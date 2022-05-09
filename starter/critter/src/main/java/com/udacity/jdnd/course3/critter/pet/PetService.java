package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Pet savePet(Pet pet) {
        Pet newPet = petRepository.save(pet);

        Customer customer  = newPet.getCustomer();
        List<Pet> pets = customer.getPets();
        if (!pets.contains(newPet)) {
            pets.add(newPet);
            customerRepository.save(customer);
        }

        return newPet;
    }

    public Pet getPet(Long id) {
        Pet pet;
        Optional<Pet> optionalPet = petRepository.findById(id);
        if (optionalPet.isPresent())
            pet = optionalPet.get();
        else
            throw new PetNotFoundException("Pet not found");

        return pet;
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(Long ownerId) {
        return petRepository.findAllByCustomerId(ownerId);
    }
}
