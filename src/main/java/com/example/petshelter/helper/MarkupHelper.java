package com.example.petshelter.helper;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MarkupHelper {

    public InlineKeyboardMarkup buildMenu(@NotNull Map<String, String> menu) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        for (Map.Entry<String, String> entry : menu.entrySet()) {
            keyboardMarkup.addRow(new InlineKeyboardButton(entry.getValue()).callbackData(entry.getKey()));
        }
        return keyboardMarkup;
    }

}
