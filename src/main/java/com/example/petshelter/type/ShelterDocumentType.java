package com.example.petshelter.type;

public enum ShelterDocumentType {

    SHORT_DESCRIPTION("short description"),
    DRIVING_DIRECTIONS("driving directions"),
    ENTRY_PASS("entry pass"),
    SAFETY_RULES("safety rules");

    private final String title;

    ShelterDocumentType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
