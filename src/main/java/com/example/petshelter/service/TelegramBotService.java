package com.example.petshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
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
     * @param chatId
     * @param text
     * @param keyboard
     * @param parseMode
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
            logger.info("Response OK: {}", response.isOk());
            if (!response.isOk()) {
                logger.error("Response fail: {}",  response.description());
            }
            logger.info("SendMessage1 TelegramBotService");
        } catch (Exception e) {
            logger.error("Error SendMessage1 TelegramBotService {} ", e.getMessage());
        }
    }

    public void sendMessage(final Long chatId, final String text) {
        try {
            sendMessage(chatId, text, null, null);
            logger.info("SendMessage2 TelegramBOtService");
        } catch (Exception e) {
            logger.error("Error SendMessage2 TelegramBotService {}", e.getMessage());
        }
    }

}
