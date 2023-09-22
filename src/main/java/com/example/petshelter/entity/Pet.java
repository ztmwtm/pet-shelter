package com.example.petshelter.entity;

import com.example.petshelter.util.PetType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String species;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private PetType petType;
    private LocalDate dayOfAdopt;
    private int daysToAdaptation;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Pet(Long id, String species, String nickname, PetType petType, LocalDate dayOfAdopt, int daysToAdaptation, Shelter shelter, User user) {
        this.id = id;
        this.species = species;
        this.nickname = nickname;
        this.petType = petType;
        this.dayOfAdopt = dayOfAdopt;
        this.daysToAdaptation = daysToAdaptation;
        this.shelter = shelter;
        this.user = user;
    }

    public Pet() {
    }
    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getDayOfAdopt() {
        return dayOfAdopt;
    }

    public void setDayOfAdopt(LocalDate dayOfAdopt) {
        this.dayOfAdopt = dayOfAdopt;
    }

    public int getDaysToAdaptation() {
        return daysToAdaptation;
    }

    public void setDaysToAdaptation(int daysToAdaptation) {
        this.daysToAdaptation = daysToAdaptation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id) && Objects.equals(species, pet.species) && Objects.equals(nickname, pet.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, species, nickname);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", species='" + species + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }

}
