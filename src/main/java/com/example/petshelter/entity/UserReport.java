package com.example.petshelter.entity;

import com.example.petshelter.util.UserReportStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "user_reports")
public class UserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String petDiet;
    private String health;
    private String behavior;
    private LocalDate dateOfCreation;

    @Enumerated(EnumType.STRING)
    private UserReportStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public UserReport(Long id, String petDiet, String health, String behavior, LocalDate dateOfCreation, UserReportStatus status, User user, Pet pet) {
        this.id = id;
        this.petDiet = petDiet;
        this.health = health;
        this.behavior = behavior;
        this.dateOfCreation = dateOfCreation;
        this.status = status;
        this.user = user;
        this.pet = pet;
    }

    public UserReport() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPetDiet() {
        return petDiet;
    }

    public void setPetDiet(String petDiet) {
        this.petDiet = petDiet;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public UserReportStatus getStatus() {
        return status;
    }

    public void setStatus(UserReportStatus status) {
        this.status = status;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserReport that = (UserReport) o;
        return Objects.equals(id, that.id) && Objects.equals(petDiet, that.petDiet) && Objects.equals(health, that.health) && Objects.equals(behavior, that.behavior) && Objects.equals(dateOfCreation, that.dateOfCreation) && status == that.status && Objects.equals(user, that.user) && Objects.equals(pet, that.pet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, petDiet, health, behavior, dateOfCreation, user, pet);
    }

    @Override
    public String toString() {
        return "UserReport{" +
                "id=" + id +
                ", petDiet='" + petDiet + '\'' +
                ", health='" + health + '\'' +
                ", behavior='" + behavior + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", status=" + status +
                ", user=" + user +
                ", pet=" + pet +
                '}';
    }
}
