package com.example.petshelter.entity;

import com.example.petshelter.util.UserRole;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String firstName;
    private String lastName;
    private String tgUsername;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(Long chatId, String firstName, String lastName, String tgUsername, String phoneNumber, UserRole role) {
        this.chatId = chatId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tgUsername = tgUsername;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public User() {
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTgUsername() {
        return tgUsername;
    }

    public void setTgUsername(String tgUsername) {
        this.tgUsername = tgUsername;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(chatId, user.chatId) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(tgUsername, user.tgUsername) && Objects.equals(phoneNumber, user.phoneNumber) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, firstName, lastName, tgUsername, phoneNumber, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", tgUsername='" + tgUsername + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role.getTitle() + '\'' +
                '}';
    }
}
