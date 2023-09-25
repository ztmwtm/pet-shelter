package com.example.petshelter.controller;

import com.example.petshelter.entity.User;
import com.example.petshelter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("user")
@Tag(name = "Контроллер для управления пользователями")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(
            summary = "Добавление пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Добавленное пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    )
            }
    )
    
    @PostMapping // POST http://localhost:8080/user
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        return ResponseEntity.ok(addedUser);
    }

    @Operation(
            summary = "Поиск пользователя по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найденный пользователь по id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    )
            }
    )

    @GetMapping("{id}") // GET http://localhost:8080/user/1
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Получение списка всех пользователей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список всех пользователей",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = User.class))
                            )
                    )
            }
    )

    @GetMapping("/all") // GET http://localhost:8080/user/all
    public ResponseEntity<Collection<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
            summary = "Обновление пользователя по id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Редактируемый пользователь",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            )
    )
    
    @PutMapping("/{id}") // PUT http://localhost:8080/user/1
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }
    @Operation(
            summary = "Удаление пользователя по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный пользователь",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    )
            }
    )

    @DeleteMapping("{id}") // DELETE http://localhost:8080/user/1
    public ResponseEntity<User> deleteUserById(@PathVariable Long id) {
        User deletedUser = userService.deleteUserById(id);
        return ResponseEntity.ok(deletedUser);
    }
}
