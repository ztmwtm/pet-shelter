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
    public static String activeMenu = "";

    public void handle(Update update) {
        try {
            if (update.message() != null) {
                log.info("Message is not NULL");
                Message message = update.message();
                User user = message.from();
                Chat chat = message.chat();

                if (message.contact() != null) {
                    log.info("Contact is not NULL");
                    Contact contact = message.contact();
                    contactHandler.handle(user, chat, contact);
                } else if ((update.message().photo() != null)&&("handleReport".equals(UpdateHandler.activeMenu))){
                    log.info("Photo is not NULL");
                    photoUploadHandler.handleFromImage(user, chat, message);
                } else if ((update.message().document() != null)&&("handleReport".equals(UpdateHandler.activeMenu))){
                    log.info("Document is not NULL");
                    photoUploadHandler.handleFromDocument(user, chat, message);
                } else {
                    String text = message.text();
                    commandHandler.handle(user, chat, text);
                }
            } else if (update.callbackQuery() != null) {
                log.info("CallbackQuery is not NULL");
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
