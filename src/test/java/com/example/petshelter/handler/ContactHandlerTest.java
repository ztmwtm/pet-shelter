package com.example.petshelter.handler;

import com.example.petshelter.entity.User;
import com.example.petshelter.service.UserService;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Contact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class ContactHandlerTest {

    @Mock
    private UserService userServiceMock;

    @Test
    void handlePositiveTest() throws NoSuchFieldException, IllegalAccessException {
        Chat chat = new Chat();
        Field idField = Chat.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(chat, 1L);

        Contact contact = new Contact();
        Field phoneNumber = Contact.class.getDeclaredField("phone_number");
        phoneNumber.setAccessible(true);
        phoneNumber.set(contact, "+79569878589");
        String newNumber = contact.phoneNumber();

        User user = new User();
        Field userName = User.class.getDeclaredField("firstName");
        userName.setAccessible(true);
        userName.set(user, "Hercules");
        Field userPhoneNumber = User.class.getDeclaredField("phoneNumber");
        userPhoneNumber.setAccessible(true);
        userPhoneNumber.set(user, "+79569878589");

        doNothing().when(userServiceMock).updateUserPhoneNumber(chat.id(), newNumber);
        userServiceMock.updateUserPhoneNumber(chat.id(), newNumber);

        assertThat(user.getPhoneNumber()).isNotEmpty();
        assertThat(user.getPhoneNumber()).isEqualTo(newNumber);
    }

    @Test
    void handleNegativeTest() throws NoSuchFieldException, IllegalAccessException {
        Chat chat = new Chat();
        Field idField = Chat.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(chat, 1L);

        Contact contact = new Contact();
        Field phoneNumber = Contact.class.getDeclaredField("phone_number");
        phoneNumber.setAccessible(true);
        phoneNumber.set(contact, "+79569878580");
        String newNumber = contact.phoneNumber();

        User user = new User();
        Field userName = User.class.getDeclaredField("firstName");
        userName.setAccessible(true);
        userName.set(user, "Hercules");
        Field userPhoneNumber = User.class.getDeclaredField("phoneNumber");
        userPhoneNumber.setAccessible(true);
        userPhoneNumber.set(user, "+79569878589");

        doNothing().when(userServiceMock).updateUserPhoneNumber(chat.id(), newNumber);
        userServiceMock.updateUserPhoneNumber(chat.id(), newNumber);

        assertThat(user.getPhoneNumber()).isNotEmpty();
        assertThat(user.getPhoneNumber()).isNotEqualTo(newNumber);
    }

}