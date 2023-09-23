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
        // Создание мока пользователя и настройка мока репозитория
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        // Вызов метода, который мы тестируем
        User result = userService.addUser(user);

        // Проверка, что метод save был вызван с правильным пользователем
        verify(userRepository).save(user);

        // Проверка, что результат равен пользователю, возвращенному репозиторием
        assertEquals(user, result);
    }

    @Test
    public void testUpdateUser() {
        // Создание мока пользователя и настройка мока репозитория
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Вызов метода, который мы тестируем
        User result = userService.updateUser(userId, user);

        // Проверка, что метод findById был вызван с правильным аргументом
        verify(userRepository).findById(userId);

        // Проверка, что метод save был вызван с правильным пользователем
        verify(userRepository).save(user);

        // Проверка, что результат равен пользователю, возвращенному репозиторием
        assertEquals(user, result);
    }

    @Test
    public void testGetUserById() {
        // Создание мока пользователя и настройка мока репозитория
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Вызов метода, который мы тестируем
        User result = userService.getUserById(userId);

        // Проверка, что метод findById был вызван с правильным аргументом
        verify(userRepository).findById(userId);

        // Проверка, что результат равен пользователю, возвращенному репозиторием
        assertEquals(user, result);
    }

    @Test
    public void testGetUserByChatId() {
        // Создание мока пользователя и настройка мока репозитория
        Long chatId = 12345L;
        User user = new User();
        when(userRepository.findUserByChatId(chatId)).thenReturn(Optional.of(user));

        // Вызов метода, который мы тестируем
        User result = userService.getUserByChatId(chatId);

        // Проверка, что метод findUserByChatId был вызван с правильным аргументом
        verify(userRepository).findUserByChatId(chatId);

        // Проверка, что результат равен пользователю, возвращенному репозиторием
        assertEquals(user, result);
    }

    @Test
    public void testGetAllUsers() {
        // Создание списка моков пользователей и настройка мока репозитория
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);

        // Вызов метода, который мы тестируем
        Collection<User> result = userService.getAllUsers();

        // Проверка, что метод findAll был вызван
        verify(userRepository).findAll();

        // Проверка, что результат равен списку пользователей, возвращенному репозиторием
        assertEquals(userList, result);
    }

    @Test
    public void testDeleteUserById() {
        // Создание мока пользователя и настройка мока репозитория
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Вызов метода, который мы тестируем
        User result = userService.deleteUserById(userId);

        // Проверка, что метод findById был вызван с правильным аргументом
        verify(userRepository).findById(userId);

        // Проверка, что метод deleteById был вызван с правильным аргументом
        verify(userRepository).deleteById(userId);

        // Проверка, что результат равен пользователю, возвращенному репозиторием
        assertEquals(user, result);
    }
}
