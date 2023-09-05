package com.example.petshelter.controller;

import com.example.petshelter.entity.Shelter;
import com.example.petshelter.service.ShelterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("shelter")
public class ShelterController {

    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PostMapping // POST http://localhost:8080/shelter
    public ResponseEntity<Shelter> addShelter(@RequestBody Shelter shelter) {
        Shelter addedShelter = shelterService.addShelter(shelter);
        return ResponseEntity.ok(addedShelter);
    }

    @GetMapping("{id}") // GET http://localhost:8080/shelter/1
    public ResponseEntity<Shelter> getShelterById(@PathVariable Long id) {
        Shelter shelter = shelterService.getShelterById(id);
        if (shelter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shelter);
    }

    @GetMapping("/all") // GET http://localhost:8080/shelter/all
    public ResponseEntity<Collection<Shelter>> getAllShelters() {
        return ResponseEntity.ok(shelterService.getAllShelters());
    }

    @PutMapping("/{id}") // PUT http://localhost:8080/shelter/1
    public ResponseEntity<Shelter> updateShelter(@PathVariable("id") long id, @RequestBody Shelter shelter) {
        Shelter updatedShelter = shelterService.updateShelter(id, shelter);
        return ResponseEntity.ok(updatedShelter);
    }

    @DeleteMapping("{id}") // DELETE http://localhost:8080/shelter/1
    public ResponseEntity<Shelter> deleteShelterById(@PathVariable Long id) {
        Shelter deletedShelter = shelterService.deleteShelterById(id);
        return ResponseEntity.ok(deletedShelter);
    }

}
