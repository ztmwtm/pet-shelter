package com.example.petshelter.service;

import com.example.petshelter.listener.TelegramBotUpdatesListener;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Service
public class TelegramBotService {

    private final TelegramBot telegramBot;
    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("test/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(TelegramBotUpdatesListener.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }

    public TelegramBotService(final TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

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
            // TODO: logger
        /*logger.info("Response: {}", response.isOk());
        if (!response.isOk()) {
            logger.error("SendResponse failed with error: {}", response.description());
        }*/
            LOGGER. log(Level.INFO, "SendMessage №1 TelegramBotService");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error SendMessage №1 TelegramBotService", e);
        }
    }

    public void sendMessage(final Long chatId, final String text) {
        try {
            sendMessage(chatId, text, null, null);
            LOGGER.log(Level.INFO, "SendMessage №2 TelegramBotService");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error SendMessage №2 TelegramBotService", e);
        }
    }

}
