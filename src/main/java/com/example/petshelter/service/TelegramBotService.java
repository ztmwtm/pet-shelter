package com.example.petshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/**
 * Класс отвечающий за отправку сообщений ботом
 */

@Slf4j
@Service
public class TelegramBotService {

    private final TelegramBot telegramBot;

    public TelegramBotService(final TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        log.info("Constructor TelegramBotService");
    }

    /**
     * Метод отвечающий за отправку сообщений ботом
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
            System.out.println("Response OK: " + response.isOk());
            if (!response.isOk()) {
                System.out.println(response.description());
            }
            log.info("SendMessage1 TelegramBotService");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error SendMessage1 TelegramBotService");
        }
    }

    public void sendMessage(final Long chatId, final String text) {
        try {
            sendMessage(chatId, text, null, null);
            log.info("SendMessage2 TelegramBOtService");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error SendMessage2 TelegramBotService");
        }
    }

}