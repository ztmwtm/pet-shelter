package com.example.petshelter.repository;

import com.example.petshelter.entity.Pet;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

@NotNull
List<Pet> findAll();

}
