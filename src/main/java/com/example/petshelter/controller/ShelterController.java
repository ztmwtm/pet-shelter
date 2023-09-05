package com.example.petshelter.controller;

import com.example.petshelter.entity.Shelter;
import com.example.petshelter.service.ShelterService;
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
@RequestMapping("shelter")
public class ShelterController {

    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @Operation(
            summary = "Добавление приюта",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавленный приют",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Shelter.class)
                            )
                    )
            }
    )
    @PostMapping // POST http://localhost:8080/shelter
    public ResponseEntity<Shelter> addShelter(@RequestBody Shelter shelter) {
        Shelter addedShelter = shelterService.addShelter(shelter);
        return ResponseEntity.ok(addedShelter);
    }

    @Operation(
            summary = "Поиск приюта по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный приют по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Shelter.class)
                            )
                    )
            }
    )

    @GetMapping("{id}") // GET http://localhost:8080/shelter/1
    public ResponseEntity<Shelter> getShelterById(@PathVariable Long id) {
        Shelter shelter = shelterService.getShelterById(id);
        if (shelter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shelter);
    }

    @Operation(
            summary = "Получение списка всех приютов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список всех приютов",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
                            )
                    )
            }
    )

    @GetMapping("/all") // GET http://localhost:8080/shelter/all
    public ResponseEntity<Collection<Shelter>> getAllShelters() {
        return ResponseEntity.ok(shelterService.getAllShelters());
    }

    @Operation(
            summary = "Обновление приюта по id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактируемый приют",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Shelter.class)
                    )
            )
    )

    @PutMapping("/{id}") // PUT http://localhost:8080/shelter/1
    public ResponseEntity<Shelter> updateShelter(@PathVariable("id") long id, @RequestBody Shelter shelter) {
        Shelter updatedShelter = shelterService.updateShelter(id, shelter);
        return ResponseEntity.ok(updatedShelter);
    }

    @Operation(
            summary = "Удаление приюта по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный приют",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Shelter.class)
                            )
                    )
            }
    )

    @DeleteMapping("{id}") // DELETE http://localhost:8080/shelter/1
    public ResponseEntity<Shelter> deleteShelterById(@PathVariable Long id) {
        Shelter deletedShelter = shelterService.deleteShelterById(id);
        return ResponseEntity.ok(deletedShelter);
    }

}
