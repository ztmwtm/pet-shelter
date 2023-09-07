package com.example.petshelter.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum UserRole {
    ADMINISTRATOR("administrator"),
    USER("user"),
    VOLUNTEER("volunteer");
    private final String title;

    UserRole(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}