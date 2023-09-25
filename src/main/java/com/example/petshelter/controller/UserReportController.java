package com.example.petshelter.controller;

import com.example.petshelter.entity.UserReport;
import com.example.petshelter.entity.UserReportPhoto;
import com.example.petshelter.service.UserReportPhotoService;
import com.example.petshelter.service.UserReportService;
import com.pengrad.telegrambot.model.Document;
import com.pengrad.telegrambot.model.PhotoSize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("report")
public class UserReportController {

    private final UserReportService userReportService;
    private final UserReportPhotoService userReportPhotoService;

    public UserReportController(UserReportService userReportService, UserReportPhotoService userReportPhotoService) {
        this.userReportService = userReportService;
        this.userReportPhotoService = userReportPhotoService;
    }

    @Operation(
            summary = "Добавление отчета",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавленный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserReport.class)
                            )
                    )
            }
    )
    
    @PostMapping // POST http://localhost:8080/report
    public ResponseEntity<UserReport> addUserReport(@RequestBody UserReport userReport) {
        UserReport addedUserReport = userReportService.addUserReport(userReport);
        return ResponseEntity.ok(addedUserReport);
    }

    @Operation(
            summary = "Поиск отчета по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный отчет по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserReport.class)
                            )
                    )
            }
    )

    @GetMapping("{id}") // GET http://localhost:8080/report/1
    public ResponseEntity<UserReport> getUserReportById(@PathVariable Long id) {
        UserReport userReport = userReportService.getUserReportById(id);
        if (userReport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userReport);
    }

    @Operation(
            summary = "Получение списка всех отчетов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список всех отчетов",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = UserReport.class))
                            )
                    )
            }
    )

    @GetMapping("/all") // GET http://localhost:8080/report/all
    public ResponseEntity<Collection<UserReport>> getAllUserReports() {
        return ResponseEntity.ok(userReportService.getAllUserReports());
    }

    @Operation(
            summary = "Обновление отчета по id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактируемый отчет",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserReport.class)
                    )
            )
    )
    
    @PutMapping("/{id}") // PUT http://localhost:8080/user/1
    public ResponseEntity<UserReport> updateUserReport(@PathVariable("id") long id, @RequestBody UserReport userReport) {
        UserReport updatedUserReport = userReportService.updateUserReport(id, userReport);
        return ResponseEntity.ok(updatedUserReport);
    }
    @Operation(
            summary = "Удаление отчета по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserReport.class)
                            )
                    )
            }
    )

    @DeleteMapping("{id}") // DELETE http://localhost:8080/user/1
    public ResponseEntity<UserReport> deleteUserReportById(@PathVariable Long id) {
        UserReport deletedUserReport = userReportService.deleteUserReportById(id);
        return ResponseEntity.ok(deletedUserReport);
    }

    @Operation(
            summary = "Добавление фото к отчету",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавленное фото к отчету",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserReportPhoto.class)
                            )
                    )
            }
    )

    @PostMapping(value = "{userReportId}/photo")
    public ResponseEntity<String> uploadsPhotos(@PathVariable Long userReportId, @RequestParam PhotoSize... resources) throws IOException {
        userReportPhotoService.uploadsPhotos(userReportId,resources);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Добавление фото к отчету",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавленное фото к отчету",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserReportPhoto.class)
                            )
                    )
            }
    )

    @PostMapping(value = "{userReportId}/document")
    public ResponseEntity<String> uploadsDocuments(@PathVariable Long userReportId, @RequestParam Document document) throws IOException {
        userReportPhotoService.uploadsDocuments(userReportId,document);
        return ResponseEntity.ok().build();
    }

}
