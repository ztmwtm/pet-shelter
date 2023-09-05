package com.example.petshelter.listener;

import com.example.petshelter.handler.UpdateHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final UpdateHandler updateHandler;
    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("test/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(TelegramBotUpdatesListener.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }

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
        try {
            telegramBot.setUpdatesListener(this);
            LOGGER.log(Level.INFO, "Annotation @PostConstructor init UpdatesListener");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error annotation @PostConstructor init UpdatesListener", e);
        }
    }

    @Override
    public int process(@NotNull List<Update> updates) {
        try {
            updates.forEach(updateHandler::handle);
            LOGGER.log(Level.INFO, "Procces UpdatesListener");
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error Process UpdatesListener", e);
        }
        return 0;
    }

}
