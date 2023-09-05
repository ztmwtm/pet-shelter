package com.example.petshelter.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum CallbackData {

    CATS("cats", "Выберите, что вы хотите узнать о приюте для кошек:"),
    DOGS("dogs", "Выберите, что вы хотите узнать о приюте для собак:"),

    CATS_INFO("cats.info", "Узнать информацию о приюте"),
    CATS_TAKE("cats.take", "Как взять кошку из приюта"),
    DOGS_INFO("dogs.info", "Узнать информацию о приюте для собак"),
    DOGS_TAKE("dogs.take", "Как взять собаку из приюта"),

    REPORT("report", "Прислать отчет о питомце"),
    HELP("help", "Позвать волонтера");

    private final String title;
    private final String description;

    CallbackData(final String title, final String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        try {
            log.info("GetTitle " + title + " CallbackData");
            return title;
        } catch (Exception e) {
            log.error(e.getMessage() + "Error GetTitle CallbackData");
        }
        return null;
    }

    public String getDescription() {
        try {
            log.info("GetDescription " + getTitle() + " CallbackData");
            return description;
        } catch (Exception e) {
            log.error(e.getMessage() + "Error GetDescription " + getTitle() + "CallbackData");
        }
        return null;
    }

}
