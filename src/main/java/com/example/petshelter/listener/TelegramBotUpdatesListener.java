package com.example.petshelter.listener;

import com.example.petshelter.handler.UpdateHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final UpdateHandler updateHandler;

    @Autowired
    public TelegramBotUpdatesListener(@NotNull final TelegramBot telegramBot,
                                      final UpdateHandler updateHandler) {
        this.telegramBot = telegramBot;
        this.updateHandler = updateHandler;

        BaseResponse response = telegramBot.execute(new SetMyCommands(
                new BotCommand("/start", "Начало работы")
        ));
        // TODO: logger response
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(@NotNull List<Update> updates) {
        updates.forEach(updateHandler::handle);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}