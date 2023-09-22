package com.example.petshelter.type;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum UserRole {
    ADMINISTRATOR("administrator"),
    USER("user"),
    ADOPTER("adopter"),
    VOLUNTEER("volunteer");
    private final String title;

    UserRole(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
