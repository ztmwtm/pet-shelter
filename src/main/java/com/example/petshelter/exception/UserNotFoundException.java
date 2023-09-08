package com.example.petshelter.exception;

public class UserNotFoundException extends RuntimeException{
    private final long chatId;
    public UserNotFoundException(long chatId) {
        this.chatId = chatId;
    }
    @Override
    public String getMessage() {
        return "Пользователь с chat id = " + chatId + " не найден!";
    }


}
