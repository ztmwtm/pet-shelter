package com.example.petshelter.repository;

import com.example.petshelter.entity.User;
import com.example.petshelter.util.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByChatId(long id);
    com.pengrad.telegrambot.model.User findUserByRole(UserRole role);
}
