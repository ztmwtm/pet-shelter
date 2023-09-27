package com.example.petshelter.entity;

import com.example.petshelter.type.PetType;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shelters")
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String workSchedule;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shelter")
    private List<ShelterDocument> shelterDocuments;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PetType petType;
    private float latitude;
    private float longitude;

    public Shelter(final String name,
                   final String address,
                   final String phoneNumber,
                   final String workSchedule,
                   final PetType petType,
                   final float latitude,
                   final float longitude) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.workSchedule = workSchedule;
        this.petType = petType;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Shelter() {
    }

    public List<ShelterDocument> getShelterDocuments() {
        return shelterDocuments;
    }

    public void setShelterDocuments(final List<ShelterDocument> shelterDocuments) {
        this.shelterDocuments = shelterDocuments;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(final PetType petType) {
        this.petType = petType;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        return Objects.equals(id, shelter.id) && Objects.equals(name, shelter.name) && Objects.equals(address, shelter.address) && Objects.equals(phoneNumber, shelter.phoneNumber) && Objects.equals(workSchedule, shelter.workSchedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phoneNumber, workSchedule);
    }

    @Override
    public String toString() {
        return "Shelter{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", address='" + address + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               ", workSchedule='" + workSchedule + '\'' +
               ", shelterDocuments=" + shelterDocuments +
               ", petType=" + petType +
               ", latitude=" + latitude +
               ", longitude=" + longitude +
               '}';
    }

}
