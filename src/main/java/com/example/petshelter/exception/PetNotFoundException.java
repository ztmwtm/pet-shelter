package com.example.petshelter.exception;

public class PetNotFoundException extends RuntimeException{
    private final long id;
    public PetNotFoundException(long id) {
        this.id = id;
    }
    @Override
    public String getMessage() {
        return "Животное с id = " + id + " не найдено!";
    }


}
