package com.example.petshelter.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_reports")
public class UserReport {
    @Id
    @GeneratedValue
    private Long id;
    private String petDiet;
    private String health;
    private String behavior;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public UserReport(Long id, String petDiet, String health, String behavior, User user, Pet pet, Shelter shelter) {
        this.id = id;
        this.petDiet = petDiet;
        this.health = health;
        this.behavior = behavior;
        this.user = user;
        this.pet = pet;
        this.shelter = shelter;
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
    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserReport that = (UserReport) o;
        return Objects.equals(id, that.id) && Objects.equals(petDiet, that.petDiet) && Objects.equals(health, that.health) && Objects.equals(behavior, that.behavior) && Objects.equals(user, that.user) && Objects.equals(pet, that.pet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, petDiet, health, behavior, user, pet);
    }

    @Override
    public String toString() {
        return "UserReport{" +
                "id=" + id +
                ", petDiet='" + petDiet + '\'' +
                ", health='" + health + '\'' +
                ", behavior='" + behavior + '\'' +
                ", user=" + user +
                ", pet=" + pet +
                '}';
    }
}
