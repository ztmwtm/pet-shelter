package com.example.petshelter.exception;

public class ShelterNotFoundException extends RuntimeException{
    private final long id;
    public ShelterNotFoundException(long id) {
        this.id = id;
    }
    @Override
    public String getMessage() {
        return "Приют с id = " + id + " не найдено!";
    }


}
