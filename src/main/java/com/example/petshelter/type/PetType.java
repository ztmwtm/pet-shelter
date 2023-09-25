package com.example.petshelter.type;

public enum PetType {
    DOG("DOG"),
    CAT("CAT");

    private final String title;

    PetType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
