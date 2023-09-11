package com.example.petshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendLocation;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

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
}
