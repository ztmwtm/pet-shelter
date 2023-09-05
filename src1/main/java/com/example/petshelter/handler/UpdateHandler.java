package com.example.petshelter.handler;

import com.pengrad.telegrambot.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Component
public class UpdateHandler {

    private final CommandHandler commandHandler;
    private final CallbackQueryHandler callbackQueryHandler;
    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("test/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(UpdateHandler.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }

    @Autowired
    public UpdateHandler(final CommandHandler commandHandler,
                         final CallbackQueryHandler callbackQueryHandler) {
        this.commandHandler = commandHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        LOGGER.log(Level.INFO, "Constructor UpdateHandler");
    }

    public void handle(Update update) {
        try {
            if (update.message() != null) {
                Message message = update.message();
                User user = message.from();
                Chat chat = message.chat();
                String text = message.text();
                commandHandler.handle(user, chat, text);
            } else if (update.callbackQuery() != null) {
                CallbackQuery query = update.callbackQuery();
                User user = query.from();
                Chat chat = query.message().chat();
                String data = query.data();
                callbackQueryHandler.handle(user, chat, data);
            }
            LOGGER.log(Level.INFO, "Handle UpdateHandler");
        }catch (Exception e) {
            LOGGER.log(Level.INFO, "Error Handle UpdateHandler");
        }
    }

}
