package com.example.petshelter.util;

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
        return title;
    }

    public String getDescription() {
        return description;
    }

}
