package com.example.petshelter.service;

import com.example.petshelter.entity.User;
import com.example.petshelter.exception.UserNotFoundException;
import com.example.petshelter.repository.UserRepository;
import com.example.petshelter.type.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User addUser(User user) {
        logger.info("Was called method to add user");
        return userRepository.save(user);
    }


    public User updateUser(Long id, User user) {
        logger.info("Was called method to update user with id {}", id);
        User oldUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        oldUser.setChatId(user.getChatId());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setTgUsername(user.getTgUsername());
        oldUser.setPhoneNumber(user.getPhoneNumber());
        oldUser.setRole(user.getRole());
        return userRepository.save(oldUser);
    }

    public User getUserById(Long id) {
        logger.info("Was called method to get user with id {}", id);
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

    }

    public User getUserByChatId(Long chatId) {
        logger.info("Was called method to get user by chatId {}", chatId);
        return userRepository.findUserByChatId(chatId).orElse(null);
    }

    public Collection<User> getAllUsers() {
        logger.info("Was called method to get all users");
        return userRepository.findAll();
    }

    public User deleteUserById(Long id) {
        logger.info("Was called method to delete user with id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
        return user;
    }

    public void updateUserSelectedShelterId(Long chatId, String data) {
        User oldUser =  userRepository.findUserByChatId(chatId).orElseThrow(() -> new UserNotFoundException(chatId));
        oldUser.setSelectedShelterId(Long.parseLong(data));
        userRepository.save(oldUser);
    }

    public void resetSelectedShelterId(Long chatId) {
        User oldUser = userRepository.findUserByChatId(chatId).orElseThrow(() -> new UserNotFoundException(chatId));
        oldUser.setSelectedShelterId(0);
        userRepository.save(oldUser);
    }

    public void registerNewUser(final com.pengrad.telegrambot.model.User user, final Long chatId) {
        User newUser = new User();
        newUser.setChatId(chatId);
        newUser.setFirstName(user.firstName());
        newUser.setLastName(user.lastName());
        newUser.setTgUsername(user.username());
        newUser.setRole(UserRole.USER);
        newUser.setPhoneNumber(null);
        this.addUser(newUser);
        logger.info("Was called method to register User: {}", newUser);
    }

    public void updateUserPhoneNumber(final Long chatId, final String phoneNumber) {
        User user = this.getUserByChatId(chatId);
        Long userId = user.getId();
        user.setPhoneNumber(phoneNumber);
        this.updateUser(userId, user);
        logger.info("Was called method to set phone number for UserId {}", userId);
    }
}
