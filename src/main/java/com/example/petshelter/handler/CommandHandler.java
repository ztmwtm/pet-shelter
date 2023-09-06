package com.example.petshelter.handler;

import com.example.petshelter.helper.MarkupHelper;
import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.service.UserService;
import com.example.petshelter.util.CallbackData;
import com.example.petshelter.util.Command;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Класс отвечающий за приветствие пользователя и предлагающий выбор приюта
 */
@Slf4j
@Component
public class CommandHandler {

    /**
     * Текст выводящийся в приветствии
     */
    private static final String GREETING = """
            , привет!
            Я - бот-помощник приюта домашних животных.
            Начните с выбора приюта:""";
    private final Map<Command, BiConsumer<User, Chat>> commandExecutors = new HashMap<>();
    private final TelegramBotService telegramBotService;
    private final UserService userService;
    private final MarkupHelper markupHelper;
    private final Map<String, String> mainMenu = new HashMap<>();

    /*
     * Нестатический блок инициализации метода и кнопок
     */
    {
        commandExecutors.put(Command.START, this::handleStart);
        mainMenu.put(CallbackData.CATS.getTitle(), "\uD83D\uDC08 Приют для кошек");
        mainMenu.put(CallbackData.DOGS.getTitle(), "\uD83D\uDC15 Приют для собак");
    }

    @Autowired
    public CommandHandler(final TelegramBotService telegramBotService, final UserService userService, final MarkupHelper markupHelper) {
        this.telegramBotService = telegramBotService;
        this.userService = userService;
        this.markupHelper = markupHelper;
        log.info("Constructor CommandHendler");
    }

    public void handle(User user, Chat chat, String commandText) {
        try {
            Command[] commands = Command.values();
            for (Command command : commands) {
                if (("/" + command.getTitle()).equals(commandText)) {
                    commandExecutors.get(command).accept(user, chat);
                    break;
                }
            }
            log.info("Handle CommandHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error Handle CommandHandler");
        }
    }

    /**
     * Метод отвечающий за приветствие пользователя в начале работы
     * @param user User
     * @param chat Chat
     */
    private void handleStart(User user, Chat chat) {
        try {
            Long chatId = chat.id();

            if (userService.getUserByChatId(chatId) == null) {
                String userName = user.firstName();
                registerNewUser(user, chatId);
                telegramBotService.sendMessage(chatId, userName + GREETING, markupHelper.buildMenu(mainMenu), null);
            } else {
                telegramBotService.sendMessage(chatId, "Выберите приют:", markupHelper.buildMenu(mainMenu), null);
            }

            log.info("HandleStart CommandHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleStart CommandHandler");
        }
    }

    private void registerNewUser(final User user, final Long chatId) {
        try {
            com.example.petshelter.entity.User newUser = new com.example.petshelter.entity.User();
            newUser.setChatId(chatId);
            newUser.setFirstName(user.firstName());
            newUser.setLastName(user.lastName());
            newUser.setTgUsername(user.username());
            newUser.setIsVolunteer(false);
            newUser.setIsAdopter(false);
            newUser.setPhoneNumber(null);
            userService.addUser(newUser);
            log.info("User Registered: {}", newUser);
        } catch (Exception e) {
            log.error(e.getMessage() + "Error registering a new User");
        }
    }

}
