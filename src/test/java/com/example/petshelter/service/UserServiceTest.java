package com.example.petshelter.service;

import com.example.petshelter.entity.User;
import com.example.petshelter.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser() {
        User user = new User();

        when(userRepository.save(user)).thenReturn(user);
        User result = userService.addUser(user);

        verify(userRepository).save(user);
        assertEquals(user, result);
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updateUser(userId, user);

        verify(userRepository).findById(userId);
        verify(userRepository).save(user);
        assertEquals(user, result);
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        verify(userRepository).findById(userId);
        assertEquals(user, result);
    }

    @Test
    public void testGetUserByChatId() {
        Long chatId = 12345L;
        User user = new User();

        when(userRepository.findUserByChatId(chatId)).thenReturn(Optional.of(user));

        User result = userService.getUserByChatId(chatId);

        verify(userRepository).findUserByChatId(chatId);
        assertEquals(user, result);
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(userList);

        Collection<User> result = userService.getAllUsers();

        verify(userRepository).findAll();
        assertEquals(userList, result);
    }

    @Test
    public void testDeleteUserById() {
        Long userId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.deleteUserById(userId);

        verify(userRepository).findById(userId);
        verify(userRepository).deleteById(userId);
        assertEquals(user, result);
    }
}
