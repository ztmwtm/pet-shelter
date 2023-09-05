package com.example.petshelter.helper;

import com.example.petshelter.handler.CallbackQueryHandler;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Component
public class MarkupHelper {

    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("test/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(MarkupHelper.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }

    public InlineKeyboardMarkup buildMenu(@NotNull Map<String, String> menu) {
        try {
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            for (Map.Entry<String, String> entry : menu.entrySet()) {
                keyboardMarkup.addRow(new InlineKeyboardButton(entry.getValue()).callbackData(entry.getKey()));
            }
            LOGGER.log(Level.INFO, "BuildMenu MarkupHendler");
            return keyboardMarkup;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error BuildMrnu MarkupHendler", e);
        }
        return null;
    }

}
