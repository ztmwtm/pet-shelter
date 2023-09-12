package com.example.petshelter.repository;

import com.example.petshelter.entity.Shelter;
import com.example.petshelter.util.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    Shelter findShelterByName(String name);

    List<Shelter> findShelterByPetType(PetType type);

}
