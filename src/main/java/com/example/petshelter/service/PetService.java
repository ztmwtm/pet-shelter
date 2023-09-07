package com.example.petshelter.service;

import com.example.petshelter.entity.Pet;
import com.example.petshelter.exception.PetNotFoundException;
import com.example.petshelter.repository.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final Logger logger = LoggerFactory.getLogger(PetService.class);

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet addPet(Pet pet){
        logger.info("Was called method to add pet");
        return petRepository.save(pet);
    }

    public Pet updatePet(Long id, Pet pet){
        logger.info("Was called method to update pet with id {}", id);
        Pet oldPet = petRepository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
        oldPet.setSpecies(pet.getSpecies());
        oldPet.setNickname(pet.getNickname());
        oldPet.setAdopted(pet.getAdopted());
        return petRepository.save(oldPet);
    }

    public Pet getPetById(Long id) {
        logger.info("Was called method to get pet with id {}", id);
        return petRepository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
    }

    public Collection<Pet> getAllPets() {
        logger.info("Was called method to get all pets");
        return petRepository.findAll();
    }

    public Pet deletePetById(Long id) {
        logger.info("Was called method to delete pet with id {}", id);
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
        petRepository.deleteById(id);
        return pet;
    }







}
