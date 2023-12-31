package com.example.petshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Класс отвечающий за отправку сообщений ботом
 */


@Service
public class TelegramBotService {

    private final TelegramBot telegramBot;
    private final Logger logger = LoggerFactory.getLogger(TelegramBotService.class);

    public TelegramBotService(final TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        logger.info("Constructor TelegramBotService");
    }

    /**
     * Метод отвечающий за отправку сообщений ботом
     *
     * @param chatId    Long
     * @param text      String
     * @param keyboard  InlineKeyboardMarkup
     * @param parseMode ParseMode
     */
    public void sendMessage(final Long chatId,
                            final String text,
                            @Nullable InlineKeyboardMarkup keyboard,
                            @Nullable ParseMode parseMode
    ) {
        try {
            SendMessage message = new SendMessage(chatId, text);
            if (keyboard != null) {
                message.replyMarkup(keyboard);
            }
            if (parseMode != null) {
                message.parseMode(parseMode);
            }
            SendResponse response = telegramBot.execute(message);

            if (!response.isOk()) {
                logger.error(response.description());
            }
            logger.info("SendMessage TelegramBotService");
        } catch (Exception e) {
            logger.error(e.getMessage(), "Error SendMessage TelegramBotService {}");
        }
    }

    public void sendMessage(final Long chatId, final String text) {
        sendMessage(chatId, text, null, null);
    }

    public void sendContact(final Long chatId,
                            final String text,
                            @Nullable ReplyKeyboardMarkup keyboard,
                            @Nullable ParseMode parseMode
    ) {
        try {
            SendMessage message = new SendMessage(chatId, text);
            if (keyboard != null) {
                message.replyMarkup(keyboard);
                keyboard.resizeKeyboard(true);
                keyboard.oneTimeKeyboard(true);
            }
            if (parseMode != null) {
                message.parseMode(parseMode);
            }
            telegramBot.execute(message);
        } catch (Exception e) {
            logger.error(e.getMessage(), "Error SendMessage TelegramBotService {}");
        }
    }

    public void sendPDFDocument(final Long chatId,
                                final String fileName,
                                final String caption,
                                @Nullable InlineKeyboardMarkup keyboard,
                                @Nullable ParseMode parseMode
    ) {
        SendDocument document = new SendDocument(chatId, fileName);
        if (caption != null) {
            document.caption(caption);
        }
        if (keyboard != null) {
            document.replyMarkup(keyboard);
        }
        if (parseMode != null) {
            document.parseMode(parseMode);
        }
        SendResponse response = telegramBot.execute(document);
        if (!response.isOk()) {
            logger.error(response.description());
        }
        Message message = response.message();
        logger.info(message.document().fileId());
    }

    public void sendPDFDocument(final Long chatId,
                                final String fileName,
                                final String caption
    ) {
        sendPDFDocument(chatId, fileName, caption, null, null);
    }

    public void sendPicture(final Long chatId,
                            final String fileName,
                            final String caption,
                            @Nullable InlineKeyboardMarkup keyboard,
                            @Nullable ParseMode parseMode
    ) {
        SendPhoto picture = new SendPhoto(chatId, fileName);
        if (caption != null) {
            picture.caption(caption);
        }
        if (keyboard != null) {
            picture.replyMarkup(keyboard);
        }
        if (parseMode != null) {
            picture.parseMode(parseMode);
        }
        SendResponse response = telegramBot.execute(picture);
        if (!response.isOk()) {
            logger.error(response.description());
        }
        Message message = response.message();
        logger.info(message.document().fileId());
    }

    public void sendPicture(final Long chatId,
                            final String fileName,
                            final String caption
    ) {
        sendPicture(chatId, fileName, caption, null, null);
    }

    public void sendLocation(final Long chatId, float latitude, float longitude) {
        telegramBot.execute(new SendLocation(chatId, latitude, longitude));
    }

    public URL getFileURL(String fileId) throws MalformedURLException {
        GetFile request = new GetFile(fileId);
        GetFileResponse getFileResponse = telegramBot.execute(request);
        File file = getFileResponse.file();
        return new URL(telegramBot.getFullFilePath(file));
    }

    public void sendDocument(Long chatId, String file) {
        SendDocument document = new SendDocument(chatId, file);
        telegramBot.execute(document);
    }

    public void sendPhoto(Long chatId, java.io.File photo) {
        telegramBot.execute(new SendPhoto(chatId, photo));
    }
}
