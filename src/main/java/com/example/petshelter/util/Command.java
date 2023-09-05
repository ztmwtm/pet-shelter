package com.example.petshelter.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Константа кнопки старт
 */
@Slf4j
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
        try {
            log.info("GetTitle " + title + " Command");
            return title;
        } catch (Exception e) {
            log.error(e.getMessage() + "Error GetTitle Command");
        }
        return null;
    }

    public String getDescription() {
        try {
            log.info("GetDescription " + getTitle() + " Command");
            return description;
        } catch (Exception e) {
            log.error(e.getMessage() + "Error GetDescription " + getTitle() + "Command");
        }
        return null;
    }
}