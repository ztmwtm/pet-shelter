package com.example.petshelter.controller;

import com.example.petshelter.entity.Pet;
import com.example.petshelter.service.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping // POST http://localhost:8080/pet
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
        Pet addedPet = petService.addPet(pet);
        return ResponseEntity.ok(addedPet);
    }

    @GetMapping("{id}") // GET http://localhost:8080/pet/1
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        Pet pet = petService.getPetById(id);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }

    @GetMapping("/all") // GET http://localhost:8080/pet/all
    public ResponseEntity<Collection<Pet>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @PutMapping("/{id}") // PUT http://localhost:8080/pet/1
    public ResponseEntity<Pet> updatePet(@PathVariable("id") long id, @RequestBody Pet pet) {
        Pet updatedPet = petService.updatePet(id, pet);
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("{id}") // DELETE http://localhost:8080/pet/1
    public ResponseEntity<Pet> deletePetById(@PathVariable Long id) {
        Pet deletedPet = petService.deletePetById(id);
        return ResponseEntity.ok(deletedPet);
    }

}
