package com.example.petshelter.entity;

import com.example.petshelter.type.PetStatus;
import com.example.petshelter.type.PetType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private LocalDate dayOfAdopt;
    private int daysToAdaptation;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User adopter;
    @Enumerated(EnumType.STRING)
    private PetStatus petStatus;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public Pet(final String species,
               final String nickname,
               final PetType petType) {
        this.species = species;
        this.nickname = nickname;
        this.petType = petType;
    }

    public Pet() {
    }
    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public User getAdopter() {
        return adopter;
    }

    public void setAdopter(User adopter) {
        this.adopter = adopter;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
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

    public PetStatus getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(final PetStatus petStatus) {
        this.petStatus = petStatus;
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
               ", petType=" + petType +
               ", dayOfAdopt=" + dayOfAdopt +
               ", daysToAdaptation=" + daysToAdaptation +
               ", adopter=" + adopter +
               ", petStatus=" + petStatus +
               ", shelter=" + shelter +
               '}';
    }
}
