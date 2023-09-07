package com.example.petshelter.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String species;
    private String nickname;
    private boolean adopted;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public Pet(Long id, String species, String nickname, boolean adopted, Shelter shelter) {
        this.id = id;
        this.species = species;
        this.nickname = nickname;
        this.adopted = adopted;
        this.shelter = shelter;
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

    public boolean getAdopted() {
        return adopted;
    }

    public void setAdopted(boolean adopted) {
        this.adopted = adopted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return adopted == pet.adopted && Objects.equals(id, pet.id) && Objects.equals(species, pet.species) && Objects.equals(nickname, pet.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, species, nickname, adopted);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", species='" + species + '\'' +
                ", nickname='" + nickname + '\'' +
                ", isAdopted=" + adopted +
                '}';
    }

}
