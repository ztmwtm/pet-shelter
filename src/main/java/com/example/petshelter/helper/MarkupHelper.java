package com.example.petshelter.helper;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Помощник по разметке меню под сообщениями
 */

@Slf4j
@Component
public class MarkupHelper {

    /**
     * Метод отвечающий за создание кнопок меню под сообщением
     * @param menu InlineKeyboardMarkup
     * @return InlineKeyboardMarkup
     */
    public InlineKeyboardMarkup buildMenu(@NotNull Map<String, String> menu) {
        try {
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            for (Map.Entry<String, String> entry : menu.entrySet()) {
                keyboardMarkup.addRow(new InlineKeyboardButton(entry.getValue()).callbackData(entry.getKey()));
            }
            return keyboardMarkup;
        } catch (Exception e) {
            log.error(e.getMessage() + " Error BuildMenu MarkupHandler");
        }
        return null;
    }

}
