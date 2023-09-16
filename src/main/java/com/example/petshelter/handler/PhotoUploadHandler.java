package com.example.petshelter.handler;

import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.service.UserReportPhotoService;
import com.example.petshelter.service.UserService;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class PhotoUploadHandler {

    private final Logger logger = LoggerFactory.getLogger("PhotoUploadHandler.class");
    private final TelegramBotService telegramBotService;
    private final UserReportPhotoService userReportPhotoService;
    private final UserService userService;

    public PhotoUploadHandler(TelegramBotService telegramBotService, UserReportPhotoService userReportPhotoService, UserService userService) {
        this.telegramBotService = telegramBotService;
        this.userReportPhotoService = userReportPhotoService;
        this.userService = userService;
    }


    public void handle(User user, Chat chat, Message message) {
        File photo = userReportPhotoService.uploadsPhotos(0L, message.photo());
        // telegramBotService.sendPhoto(chat.id(), photo); бот шлет нам наше же фото обратно
    }

    public void handleFromDocument(User user, Chat chat, Message message) {
        File photo = userReportPhotoService.uploadsDocuments(0L, message.document());
        // telegramBotService.sendPhoto(chat.id(), photo); бот шлет нам наше же фото обратно
    }
}
