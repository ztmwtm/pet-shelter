package com.example.petshelter.type;

public enum PetStatus {

    AVAILABLE("available"),
    CHOSEN("chosen"),
    ADOPTED("adopted"),
    KEPT("kept");

    private final String title;

    PetStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

}
