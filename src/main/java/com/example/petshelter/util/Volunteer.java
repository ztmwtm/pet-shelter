package com.example.petshelter.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Volunteer {
    CHECK_REPORT("check.report", "Просмотреть отчеты");


    private final String title;
    private final String description;

    Volunteer(String title, String description) {
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
