package com.example.petshelter.util;

/**
 * Константа кнопки старт
 */
public enum Command {

    START("start", "Начать работу");

    private final String title;
    private final String description;

    Command(final String title,
            final String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
