package com.example.petshelter.handler;

import com.example.petshelter.helper.MarkupHelper;
import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.service.UserService;
import com.example.petshelter.util.CallbackData;
import com.example.petshelter.util.Command;
import com.example.petshelter.util.UserRole;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ParseMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommandHandlerTest {

    @Mock
    private TelegramBotService telegramBotServiceMock;
    @Mock
    private UserService userServiceMock;
    @Spy
    private MarkupHelper markupHelper;
    @InjectMocks
    private CommandHandler commandHandler;
    private static User user;
    private static Chat chat;
    private static final com.example.petshelter.entity.User ourUser = new com.example.petshelter.entity.User();
    private final static Map<String, String> mainMenu = new LinkedHashMap<>();
    private final static Map<String, String> startVolunteer = new LinkedHashMap<>();

    private final static Map<String, String> mainMenuWithoutChose = new LinkedHashMap<>();

    static {
        mainMenu.put(CallbackData.CATS.getTitle(), "\uD83D\uDC08 Приют для кошек");
        mainMenu.put(CallbackData.DOGS.getTitle(), "\uD83D\uDC15 Приют для собак");
        startVolunteer.put(CallbackData.START_VOLUNTEER.getTitle(), CallbackData.START_VOLUNTEER.getDescription());

        mainMenuWithoutChose.put(CallbackData.CATS_INFO.getTitle(), CallbackData.CATS_INFO.getDescription());
        mainMenuWithoutChose.put(CallbackData.CATS_TAKE.getTitle(), CallbackData.CATS_TAKE.getDescription());
        mainMenuWithoutChose.put(CallbackData.REPORT.getTitle(), CallbackData.REPORT.getDescription());
        mainMenuWithoutChose.put(CallbackData.HELP.getTitle(), CallbackData.HELP.getDescription());
        mainMenuWithoutChose.put(CallbackData.RESET_SHELTER.getTitle(), CallbackData.RESET_SHELTER.getDescription());
    }

    @BeforeAll
    static void setUpBeforeAll() throws NoSuchFieldException, IllegalAccessException {
        chat = new Chat();
        Field idField = Chat.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(chat, 42L);
        user = new User(55L);
        Field userField = User.class.getDeclaredField("first_name");
        userField.setAccessible(true);
        userField.set(user, "UserNameUUUUsuka");
        ourUser.setId(777L);
        ourUser.setRole(UserRole.USER);
        ourUser.setSelectedShelterId(55);
    }

    @BeforeEach
    void setUpBeforeEach() {
        ourUser.setId(777L);
        ourUser.setRole(UserRole.USER);
        ourUser.setSelectedShelterId(55);
    }

    @Test
    void handleNewUserStartTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = CommandHandler.class.getDeclaredField("GREETING");
        field.setAccessible(true);
        Mockito.when(userServiceMock.getUserByChatId(Mockito.anyLong())).thenReturn(null);
        commandHandler.handle(user, chat, "/" + Command.START.getTitle());
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        Mockito.verify(userServiceMock, Mockito.times(1)).
                registerNewUser(userArgumentCaptor.capture(), idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(chat.id());
        assertThat(userArgumentCaptor.getValue()).isExactlyInstanceOf(User.class);

        Mockito.verify(telegramBotServiceMock, Mockito.times(1)).
                sendMessage(chat.id(), user.firstName() + field.get(commandHandler), markupHelper.buildMenu(mainMenu), ParseMode.Markdown);
    }

    @Test
    void handleUserStartTestWithSelectedShelterTest() {
        Mockito.when(userServiceMock.getUserByChatId(Mockito.anyLong())).thenReturn(ourUser);
        commandHandler.handle(user, chat, "/" + Command.START.getTitle());

        Mockito.verify(telegramBotServiceMock, Mockito.times(1)).
                sendMessage(chat.id(), "Выберите что вы хотите узнать о приюте:", markupHelper.buildMenu(mainMenuWithoutChose), ParseMode.Markdown);
    }

    @Test
    void handleUserStartTestWithOutSelectedShelterTest() {
        ourUser.setSelectedShelterId(0);
        Mockito.when(userServiceMock.getUserByChatId(Mockito.anyLong())).thenReturn(ourUser);
        commandHandler.handle(user, chat, "/" + Command.START.getTitle());

        Mockito.verify(telegramBotServiceMock, Mockito.times(1)).
                sendMessage(chat.id(), "Выберите приют:", markupHelper.buildMenu(mainMenu), null);
    }

    @Test
    void handleVolunteerStartTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = CommandHandler.class.getDeclaredField("GREETING_VOLUNTEER");
        field.setAccessible(true);
        ourUser.setRole(UserRole.VOLUNTEER);
        Mockito.when(userServiceMock.getUserByChatId(Mockito.anyLong())).thenReturn(ourUser);
        commandHandler.handle(user, chat, "/" + Command.START.getTitle());

        Mockito.verify(telegramBotServiceMock, Mockito.times(1)).
                sendMessage(chat.id(), (String) field.get(commandHandler), markupHelper.buildMenu(startVolunteer), ParseMode.Markdown);
    }
}