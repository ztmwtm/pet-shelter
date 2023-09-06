package com.example.petshelter.service;

import com.example.petshelter.entity.User;
import com.example.petshelter.exception.UserNotFoundException;
import com.example.petshelter.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class UserService {
    private final UserRepository UserRepository;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    public User addUser(User User){
        logger.info("Was called method to add user");
        return UserRepository.save(User);
    }

    public User updateUser(Long id, User User){
        logger.info("Was called method to update user with id {}", id);
        UserRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        User oldUser = new User();
        oldUser.setId(id);
        oldUser.setChatId(User.getChatId());
        oldUser.setFirstName(User.getFirstName());
        oldUser.setLastName(User.getLastName());
        oldUser.setTgUsername(User.getTgUsername());
        oldUser.setPhoneNumber(User.getPhoneNumber());
        oldUser.setIsVolunteer(User.getIsVolunteer());
        oldUser.setIsAdopter(User.getIsAdopter());
        return UserRepository.save(oldUser);
    }

    public User getUserById(Long id) {
        logger.info("Was called method to get user with id {}", id);
        return UserRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public Collection<User> getAllUsers() {
        logger.info("Was called method to get all users");
        return UserRepository.findAll();
    }

    public User deleteUserById(Long id) {
        logger.info("Was called method to delete user with id {}", id);
        User student = UserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        UserRepository.deleteById(id);
        return student;
    }

}
