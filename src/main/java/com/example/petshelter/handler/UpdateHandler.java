package com.example.petshelter.handler;

import com.pengrad.telegrambot.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateHandler {

    private final CommandHandler commandHandler;
    private final ContactHandler contactHandler;
    private final CallbackQueryHandler callbackQueryHandler;
    private final PhotoUploadHandler photoUploadHandler;
        public UpdateHandler(final CommandHandler commandHandler,
                         final ContactHandler contactHandler,
                         final CallbackQueryHandler callbackQueryHandler, PhotoUploadHandler photoUploadHandler) {
        this.commandHandler = commandHandler;
        this.contactHandler = contactHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        this.photoUploadHandler = photoUploadHandler;
        log.info("Constructor UpdateHandler");
    }

    public void handle(Update update) {
        try {

            //TODO продумать логику вызова)))
//            if (update.message().photo() != null) {
//                Message message = update.message();
//                User user = message.from();
//                Chat chat = message.chat();
//
//                photoUploadHandler.handle(user, chat, message);
//            }
//            if (update.message().document() != null) {
//                Message message = update.message();
//                User user = message.from();
//                Chat chat = message.chat();
//
//                photoUploadHandler.handleFromDocument(user, chat, message);
//            }
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
