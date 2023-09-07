package com.example.petshelter.repository;

import com.example.petshelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShelterRepository extends JpaRepository<Shelter, Long> {

}
