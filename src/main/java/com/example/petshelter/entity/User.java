package com.example.petshelter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private Long chatId;
    private String firstName;
    private String lastName;
    private String tgUsername;
    private String phoneNumber;
    private boolean isVolunteer;
    private boolean isAdopter;

    public User(Long id, Long chatId, String firstName, String lastName, String tgUsername, String phoneNumber, boolean isVolunteer, boolean isAdopter) {
        this.id = id;
        this.chatId = chatId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tgUsername = tgUsername;
        this.phoneNumber = phoneNumber;
        this.isVolunteer = isVolunteer;
        this.isAdopter = isAdopter;
    }

    public User() {
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

    public boolean getIsVolunteer() {
        return isVolunteer;
    }

    public void setIsVolunteer(boolean volunteer) {
        isVolunteer = volunteer;
    }

    public boolean getIsAdopter() {
        return isAdopter;
    }

    public void setIsAdopter(boolean adopter) {
        isAdopter = adopter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isVolunteer == user.isVolunteer && isAdopter == user.isAdopter && Objects.equals(id, user.id) && Objects.equals(chatId, user.chatId) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(tgUsername, user.tgUsername) && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, firstName, lastName, tgUsername, phoneNumber, isVolunteer, isAdopter);
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
                ", isVolunteer=" + isVolunteer +
                ", isAdopter=" + isAdopter +
                '}';
    }
}
