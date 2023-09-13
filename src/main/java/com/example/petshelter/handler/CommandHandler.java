package com.example.petshelter.handler;

import com.example.petshelter.helper.MarkupHelper;
import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.service.UserService;
import com.example.petshelter.util.CallbackData;
import com.example.petshelter.util.Command;
import com.example.petshelter.util.UserRole;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
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
    private static final String VOLUNTEER_MENU = """
            Привет, волонтер!
            Выбери нужную команду:\s
            1. Добавить усыновителя -> /add_adopter\s
            2. Проверить отчеты -> /check_reports\s
            3. Продлить испытательный срок -> /extend_trial\s
            4. Оставить животное у хозяина -> /keep_animal\s
            """;
    private final Map<Command, BiConsumer<User, Chat>> commandExecutors = new EnumMap<>(Command.class);
    private final TelegramBotService telegramBotService;
    private final UserService userService;
    private final MarkupHelper markupHelper;
    private final Map<String, String> mainMenu = new HashMap<>();

    /*
     * Нестатический блок инициализации метода и кнопок
     */ {
        commandExecutors.put(Command.START, this::handleStart);
        mainMenu.put(CallbackData.CATS.getTitle(), "\uD83D\uDC08 Приют для кошек");
        mainMenu.put(CallbackData.DOGS.getTitle(), "\uD83D\uDC15 Приют для собак");
    }

    @Autowired
    public CommandHandler(final TelegramBotService telegramBotService,
                          final UserService userService,
                          final MarkupHelper markupHelper) {
        this.telegramBotService = telegramBotService;
        this.userService = userService;
        this.markupHelper = markupHelper;
        log.info("Constructor CommandHandler");
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
     *
     * @param user User
     * @param chat Chat
     */
    private void handleStart(User user, Chat chat) {
        try {
            Long chatId = chat.id();

            if (userService.getUserByChatId(chatId) == null) {
                String userName = user.firstName();
                try {
                    userService.registerNewUser(user, chatId);
                    log.info("User registered successfully");
                } catch (Exception e) {
                    log.error(e.getMessage() + "Error registering a new User");
                }
                telegramBotService.sendMessage(chatId, userName + GREETING, markupHelper.buildMenu(mainMenu), null);
            } else {
                com.example.petshelter.entity.User recurringUser = userService.getUserByChatId(chatId);
                if (recurringUser.getRole() == UserRole.VOLUNTEER) {
                    telegramBotService.sendMessage(chatId, VOLUNTEER_MENU, null, null);
                } else {
                    telegramBotService.sendMessage(chatId, "Выберите приют:", markupHelper.buildMenu(mainMenu), null);
                }
            }

            log.info("HandleStart CommandHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleStart CommandHandler");
        }
    }

}
