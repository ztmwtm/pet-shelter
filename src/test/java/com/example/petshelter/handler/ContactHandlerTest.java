package com.example.petshelter.handler;

import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.service.UserService;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactHandlerTest {

    @Mock
    private TelegramBotService telegramBotServiceMock;

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    ContactHandler contactHandler;

    private static User updatedUser;
    private static Chat chat;
    private static Contact contact;

    @Test
    void handle() throws NoSuchFieldException, IllegalAccessException {
        chat = new Chat();
        Field idField = Chat.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(chat, 1000L);

        String newPhoneNumber = "888999000888777";

        updatedUser = new User(1L);

    }
}