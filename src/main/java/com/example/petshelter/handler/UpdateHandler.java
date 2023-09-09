package com.example.petshelter.handler;

import com.example.petshelter.service.UserService;
import com.pengrad.telegrambot.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateHandler {

    private final UserService userService;
    private final CommandHandler commandHandler;
    private final CallbackQueryHandler callbackQueryHandler;

    public UpdateHandler(final UserService userService,
                         final CommandHandler commandHandler,
                         final CallbackQueryHandler callbackQueryHandler) {
        this.userService = userService;
        this.commandHandler = commandHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        log.info("Constructor UpdateHandler");
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
            log.info("Handle UpdateHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error Handle UpdateHandler");
        }
    }

    public void hendlerContact(com.example.petshelter.entity.User user) {
        try {
            com.example.petshelter.entity.User userToUpdate = userService.getUserByChatId(user.getChatId());
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setPhoneNumber(user.getPhoneNumber());
            userService.addUser(userToUpdate);
            log.info("HendlerContact UpdateHendler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HendlerContact UpdateHendler");
        }
    }
}
