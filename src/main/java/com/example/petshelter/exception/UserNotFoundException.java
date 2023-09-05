package com.example.petshelter.exception;

public class UserNotFoundException extends RuntimeException{
    private final long id;
    public UserNotFoundException(long id) {
        this.id = id;
    }
    @Override
    public String getMessage() {
        return "Приют с id = " + id + " не найдено!";
    }


}
