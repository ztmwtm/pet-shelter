package com.example.petshelter.service;

import com.example.petshelter.entity.Pet;
import com.example.petshelter.entity.User;
import com.example.petshelter.exception.PetNotFoundException;
import com.example.petshelter.repository.PetRepository;
import com.example.petshelter.type.PetStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class PetService {

    private static final int DEFAULT_ADAPTATION_PERIOD = 30;
    private final PetRepository petRepository;
    private final Logger logger = LoggerFactory.getLogger(PetService.class);

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet getPetByUserId(Long userId) {
        return petRepository.getPetByAdopter_Id(userId).orElseThrow();
    }

    public Pet addPet(Pet pet) {
        logger.info("Was called method to add pet");
        return petRepository.save(pet);
    }

    public Pet updatePet(Long id, Pet pet) {
        logger.info("Was called method to update pet with id {}", id);
        Pet oldPet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
        oldPet.setSpecies(pet.getSpecies());
        oldPet.setNickname(pet.getNickname());
        oldPet.setAdopter(pet.getAdopter());
        oldPet.setShelter(pet.getShelter());
        oldPet.setPetType(pet.getPetType());
        oldPet.setPetStatus(pet.getPetStatus());
        oldPet.setDayOfAdopt(pet.getDayOfAdopt());
        oldPet.setDaysToAdaptation(pet.getDaysToAdaptation());
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

    public List<Pet> getPetsWithStatus(final PetStatus status) {
        logger.info("Was called method to fetch pets with status {}", status);
        return petRepository.findPetsByPetStatus(status);
    }

    public void changePetStatus(final Long petId, final PetStatus status) {
        Pet pet = this.getPetById(petId);
        pet.setPetStatus(status);
        this.updatePet(petId, pet);
        logger.info("Was called method to set status {} for PetId {}", status, petId);
    }

    public void makePetAdopted(final Long petId, final User user, final PetStatus status) {
        Pet pet = this.getPetById(petId);
        pet.setAdopter(user);
        pet.setPetStatus(status);
        pet.setDayOfAdopt(LocalDate.now());
        pet.setDaysToAdaptation(DEFAULT_ADAPTATION_PERIOD);
        this.updatePet(petId, pet);
        logger.info("Was called method to make Pet adopted for PetId {}", petId);
    }


    public List<Long> getPetsReadyToFinalAdopt() {
        return petRepository.getPetsIdReadyToFinalAdopt();
    }
  
    public List<Pet> getPetsForAdopter(Long userId) {
        logger.info("Was called method to get pets for adopter with Id {}", userId);
        return petRepository.getPetsByAdopter_Id(userId);
    }
}
