package com.example.petshelter.controller;

import com.example.petshelter.entity.User;
import com.example.petshelter.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping // POST http://localhost:8080/user
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        return ResponseEntity.ok(addedUser);
    }

    @GetMapping("{id}") // GET http://localhost:8080/user/1
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all") // GET http://localhost:8080/user/all
    public ResponseEntity<Collection<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}") // PUT http://localhost:8080/user/1
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("{id}") // DELETE http://localhost:8080/user/1
    public ResponseEntity<User> deleteUserById(@PathVariable Long id) {
        User deletedUser = userService.deleteUserById(id);
        return ResponseEntity.ok(deletedUser);
    }

}
