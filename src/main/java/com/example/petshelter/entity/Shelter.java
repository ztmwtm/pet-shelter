package com.example.petshelter.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shelters")
public class Shelter {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String adress;
    private String phoneNumber;
    private String workSchedule;
    @OneToMany(mappedBy = "shelter")
    private List<ShelterDocument> shelterDocuments;

    public Shelter(Long id, String name, String adress, String phoneNumber, String workSchedule) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.workSchedule = workSchedule;
    }

    public Shelter() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(id, shelter.id) && Objects.equals(name, shelter.name) && Objects.equals(adress, shelter.adress) && Objects.equals(phoneNumber, shelter.phoneNumber) && Objects.equals(workSchedule, shelter.workSchedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, adress, phoneNumber, workSchedule);
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", workSchedule='" + workSchedule + '\'' +
                '}';
    }
}
