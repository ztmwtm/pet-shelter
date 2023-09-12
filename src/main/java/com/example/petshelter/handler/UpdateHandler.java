package com.example.petshelter.handler;

import com.pengrad.telegrambot.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateHandler {

    private final CommandHandler commandHandler;
    private final ContactHandler contactHandler;
    private final CallbackQueryHandler callbackQueryHandler;

    public UpdateHandler(final CommandHandler commandHandler,
                         final ContactHandler contactHandler,
                         final CallbackQueryHandler callbackQueryHandler) {
        this.commandHandler = commandHandler;
        this.contactHandler = contactHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        log.info("Constructor UpdateHandler");
    }

    public void handle(Update update) {
        try {
            if (update.message() != null) {
                Message message = update.message();
                User user = message.from();
                Chat chat = message.chat();
                if (message.contact() != null) {
                    Contact contact = message.contact();
                    contactHandler.handle(user, chat, contact);
                } else {
                    String text = message.text();
                    commandHandler.handle(user, chat, text);
                }
            } else if (update.callbackQuery() != null) {
                CallbackQuery query = update.callbackQuery();
                User user = query.from();
                Chat chat = query.message().chat();
                String data = query.data();
                callbackQueryHandler.handle(user, chat, data);
            }
            log.info("Handle UpdateHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error Handle UpdateHandler");
        }
    }

}
