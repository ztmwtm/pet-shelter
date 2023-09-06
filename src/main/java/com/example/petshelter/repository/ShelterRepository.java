package com.example.petshelter.repository;

import com.example.petshelter.entity.Shelter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    @NotNull
    List<Shelter> findAll();

    Shelter findShelterByName(String name);

}
