package com.example.petshelter.repository;

import com.example.petshelter.entity.ShelterDocument;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelterDocumnetRepository extends JpaRepository<ShelterDocument, Long> {

    @NotNull
    List<ShelterDocument> findAll();
    //List<ShelterDocument> getShelterDocumentsByShelterId();
    //List<ShelterDocument> getShelterDocumentsByShelterIdAndTitle();
}
