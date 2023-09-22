package com.example.petshelter.handler;

import com.example.petshelter.entity.UserReport;
import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.service.UserReportPhotoService;
import com.example.petshelter.service.UserReportService;
import com.example.petshelter.service.UserService;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ParseMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PhotoUploadHandler {

    private final Logger logger = LoggerFactory.getLogger("PhotoUploadHandler.class");
    private final TelegramBotService telegramBotService;
    private final UserReportPhotoService userReportPhotoService;
    private final UserService userService;
    private final UserReportService userReportService;

    public PhotoUploadHandler(TelegramBotService telegramBotService, UserReportPhotoService userReportPhotoService, UserService userService, UserReportService userReportService) {
        this.telegramBotService = telegramBotService;
        this.userReportPhotoService = userReportPhotoService;
        this.userService = userService;
        this.userReportService = userReportService;
    }

    public void handleFromImage(User user, Chat chat, Message message) {
        logger.info("Was called method to handle uploading photo as image");

        Long userId = user.id();

        com.example.petshelter.entity.User thisUser = userService.getUserByChatId(userId);
        UserReport thisUserReport = userReportService.getUserReportByUserIdAndStatusCreated(thisUser.getId());
        userReportPhotoService.uploadsPhotos(thisUserReport.getId(), message.photo());

        logger.info("handleFromDocument PhotoUploadHandler - Add Pet Photo as image");

        String text = "Отлично! Фото загрузилось в отчет. Теперь пришлите, пожалуйста, в текстовом сообщении описание рациона питомца";
        telegramBotService.sendMessage(chat.id(), text, null, ParseMode.Markdown);

    }

    public void handleFromDocument(User user, Chat chat, Message message) {
        logger.info("Was called method to handle uploading photo as document");

        Long userId = user.id();

        com.example.petshelter.entity.User thisUser = userService.getUserByChatId(userId);
        UserReport thisUserReport = userReportService.getUserReportByUserIdAndStatusCreated(thisUser.getId());
        userReportPhotoService.uploadsDocuments(thisUserReport.getId(), message.document());

        logger.info("handleFromDocument PhotoUploadHandler - Add Pet Photo as document");

        String text = "Отлично! Фото загрузилось в отчет. Теперь пришлите, пожалуйста, в текстовом сообщении описание рациона питомца";
        telegramBotService.sendMessage(chat.id(), text, null, ParseMode.Markdown);

    }
}
