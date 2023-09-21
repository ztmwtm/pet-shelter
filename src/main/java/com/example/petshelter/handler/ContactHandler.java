package com.example.petshelter.handler;

import com.example.petshelter.exception.UserNotFoundException;
import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.service.UserService;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContactHandler {

    private final TelegramBotService telegramBotService;
    private final UserService userService;

    public ContactHandler(final TelegramBotService telegramBotService, final UserService userService) {
        this.telegramBotService = telegramBotService;
        this.userService = userService;
    }

    public void handle(final User user, final Chat chat, final Contact contact) {
        try {
            Long chatId = chat.id();

            if (userService.getUserByChatId(chatId) != null) {
                String phoneNumber = contact.phoneNumber();
                String userName = user.firstName();
                try {
                    userService.updateUserPhoneNumber(chatId, phoneNumber);
                    log.info("User successfully updated: {}", chatId);
                } catch (Exception e) {
                    log.error(e.getMessage() + "Error updating the User {}", chatId);
                }
                telegramBotService.sendMessage(chatId, userName + ", ваш номер телефона был успешно сохранен", null, null);
                log.info("Phone number {} was added to User {}", phoneNumber, chatId);
            } else {
                throw new UserNotFoundException(chatId);
            }

        } catch (Exception e) {
            log.error(e.getMessage() + "Error Handle ContactHandler");
        }
    }

}
