package com.example.petshelter.repository;

import com.example.petshelter.entity.ShelterDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelterDocumentRepository extends JpaRepository<ShelterDocument, Long> {
}
