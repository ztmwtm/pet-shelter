package com.example.petshelter.service;

import com.example.petshelter.entity.Shelter;
import com.example.petshelter.exception.ShelterNotFoundException;
import com.example.petshelter.repository.ShelterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private final Logger logger = LoggerFactory.getLogger(ShelterService.class);

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public Shelter addShelter(Shelter shelter) {
        logger.info("Was called method to add shelter");
        return shelterRepository.save(shelter);
    }

    public Shelter updateShelter(Long id, Shelter shelter) {
        logger.info("Was called method to update shelter with id {}", id);
        Shelter oldShelter = shelterRepository.findById(id).orElseThrow(() -> new ShelterNotFoundException(id));
        oldShelter.setName(shelter.getName());
        oldShelter.setAddress(shelter.getAddress());
        oldShelter.setPhoneNumber(shelter.getPhoneNumber());
        oldShelter.setWorkSchedule(shelter.getWorkSchedule());
        return shelterRepository.save(oldShelter);
    }

    public Shelter getShelterById(Long id) {
        logger.info("Was called method to get shelter with id {}", id);
        return shelterRepository.findById(id).orElseThrow(() -> new ShelterNotFoundException(id));
    }

    public Collection<Shelter> getAllShelters() {
        logger.info("Was called method to get all shelters");
        return shelterRepository.findAll();
    }

    public Shelter deleteShelterById(Long id) {
        logger.info("Was called method to delete shelter with id {}", id);
        Shelter shelter = shelterRepository.findById(id)
                .orElseThrow(() -> new ShelterNotFoundException(id));
        shelterRepository.deleteById(id);
        return shelter;
    }

}
