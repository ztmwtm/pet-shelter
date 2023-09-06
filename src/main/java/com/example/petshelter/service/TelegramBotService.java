package com.example.petshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotService {

    private final TelegramBot telegramBot;

    public TelegramBotService(final TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void sendMessage(final Long chatId,
                            final String text,
                            @Nullable InlineKeyboardMarkup keyboard,
                            @Nullable ParseMode parseMode
    ) {
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
        // TODO: logger
        /*logger.info("Response: {}", response.isOk());
        if (!response.isOk()) {
            logger.error("SendResponse failed with error: {}", response.description());
        }*/
    }

    public void sendMessage(final Long chatId, final String text) {
        sendMessage(chatId, text, null, null);
    }

}
