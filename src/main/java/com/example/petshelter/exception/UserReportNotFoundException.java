package com.example.petshelter.exception;

public class UserReportNotFoundException extends RuntimeException{
    private final long id;
    public UserReportNotFoundException(long id) {
        this.id = id;
    }
    @Override
    public String getMessage() {
        return "Отчет с id = " + id + " не найдено!";
    }


}
