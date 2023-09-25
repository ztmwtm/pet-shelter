package com.example.petshelter.repository;

import com.example.petshelter.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> getPetByAdopter_Id(Long userId);
    List<Pet> getPetsByAdopter_Id(Long userId);

}
