package com.example.petshelter.controller;

import com.example.petshelter.entity.Pet;
import com.example.petshelter.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
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

    @Operation(
            summary = "Добавление животного",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавленное животное",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    )
            }
    )

    @PostMapping // POST http://localhost:8080/pet
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
        Pet addedPet = petService.addPet(pet);
        return ResponseEntity.ok(addedPet);
    }

    @Operation(
            summary = "Поиск животного по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденное животное по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    )
            }
    )

    @GetMapping("{id}") // GET http://localhost:8080/pet/1
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        Pet pet = petService.getPetById(id);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pet);
    }

    @Operation(
            summary = "Получение списка всех животных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список всех животных",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Pet.class))
                            )
                    )
            }
    )

    @GetMapping("/all") // GET http://localhost:8080/pet/all
    public ResponseEntity<Collection<Pet>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @Operation(
            summary = "Обновление животного по id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактируемое животное",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            )
    )

    @PutMapping("/{id}") // PUT http://localhost:8080/pet/1
    public ResponseEntity<Pet> updatePet(@PathVariable("id") long id, @RequestBody Pet pet) {
        Pet updatedPet = petService.updatePet(id, pet);
        return ResponseEntity.ok(updatedPet);
    }

    @Operation(
            summary = "Удаление животного по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленное животное",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                    )
            }
    )

    @DeleteMapping("{id}") // DELETE http://localhost:8080/pet/1
    public ResponseEntity<Pet> deletePetById(@PathVariable Long id) {
        Pet deletedPet = petService.deletePetById(id);
        return ResponseEntity.ok(deletedPet);
    }

}
