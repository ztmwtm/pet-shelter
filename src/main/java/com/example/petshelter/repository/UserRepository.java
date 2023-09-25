package com.example.petshelter.repository;

import com.example.petshelter.entity.User;
import com.example.petshelter.type.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByChatId(final Long chatId);

    List<User> findUsersByRoleIs(final UserRole role);

}
