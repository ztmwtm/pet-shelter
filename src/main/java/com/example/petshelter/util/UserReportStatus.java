package com.example.petshelter.util;

public enum UserReportStatus {
    CREATED("Created"),
    ON_VERIFICATION("On verification"),
    VERIFIED("Verified"),
    DECLINED("Verified");

    private final String title;

    UserReportStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
