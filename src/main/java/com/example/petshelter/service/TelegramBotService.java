package com.example.petshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendDocument;
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
        // TODO: logger(response)
    }

    public void sendMessage(final Long chatId, final String text) {
        sendMessage(chatId, text, null, null);
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
        boolean ok = response.isOk();
        System.out.println(ok);
        Message message = response.message();
        System.out.println(message.document().fileId());
    }

}
