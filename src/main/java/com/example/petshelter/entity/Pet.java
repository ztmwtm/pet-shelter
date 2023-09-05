package com.example.petshelter.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue
    private Long id;
    private String species;
    private String nickname;
    private boolean isAdopted;

    public Pet(Long id, String species, String nickname, boolean isAdopted) {
        this.id = id;
        this.species = species;
        this.nickname = nickname;
        this.isAdopted = isAdopted;
    }

    public Pet() {
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

    public boolean getIsAdopted() {
        return isAdopted;
    }

    public void setIsAdopted(boolean adopted) {
        isAdopted = adopted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return isAdopted == pet.isAdopted && Objects.equals(id, pet.id) && Objects.equals(species, pet.species) && Objects.equals(nickname, pet.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, species, nickname, isAdopted);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", species='" + species + '\'' +
                ", nickname='" + nickname + '\'' +
                ", isAdopted=" + isAdopted +
                '}';
    }

}
