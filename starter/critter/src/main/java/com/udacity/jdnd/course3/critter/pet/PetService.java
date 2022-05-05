package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    public Pet savePet(Pet pet) {
         return petRepository.save(pet);
    }

    public Pet getPat(Long id) {
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

    public Pet getPetsByOwner(Long ownerId) {
        Pet pet;
        List<Pet> pets = petRepository.findAllByCustomerId(ownerId);
        if (pets.size() > 0)
            pet = pets.get(0);
        else
            throw new PetNotFoundException("Pet not found");
        return pet;
    }
}
