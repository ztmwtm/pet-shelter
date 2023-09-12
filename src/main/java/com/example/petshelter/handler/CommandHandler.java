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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.LinkedHashMap;
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
    private final Map<Command, BiConsumer<User, Chat>> commandExecutors = new EnumMap<>(Command.class);
    private final TelegramBotService telegramBotService;
    private final UserService userService;
    private final MarkupHelper markupHelper;
    private final Map<String, String> mainMenu = new LinkedHashMap<>();

    private final Map<String, String> mainMenuWithoutChose = new LinkedHashMap<>();

    /*
     * Нестатический блок инициализации метода и кнопок
     */ {
        commandExecutors.put(Command.START, this::handleStart);
        mainMenu.put(CallbackData.CATS.getTitle(), "\uD83D\uDC08 Приют для кошек");
        mainMenu.put(CallbackData.DOGS.getTitle(), "\uD83D\uDC15 Приют для собак");

        mainMenuWithoutChose.put(CallbackData.CATS_INFO.getTitle(), CallbackData.CATS_INFO.getDescription());
        mainMenuWithoutChose.put(CallbackData.CATS_TAKE.getTitle(), CallbackData.CATS_TAKE.getDescription());
        mainMenuWithoutChose.put(CallbackData.REPORT.getTitle(), CallbackData.REPORT.getDescription());
        mainMenuWithoutChose.put(CallbackData.HELP.getTitle(), CallbackData.HELP.getDescription());
        mainMenuWithoutChose.put(CallbackData.RESET_SHELTER.getTitle(), CallbackData.RESET_SHELTER.getDescription());

    }

    @Autowired
    public CommandHandler(final TelegramBotService telegramBotService, final UserService userService, final MarkupHelper markupHelper) {
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
            com.example.petshelter.entity.User currentUser = userService.getUserByChatId(chatId);
            String logInfo = "HandleStart CommandHandler";
            if (currentUser == null) {
                String userName = user.firstName();
                registerNewUser(user, chatId);
                telegramBotService.sendMessage(chatId, userName + GREETING, markupHelper.buildMenu(mainMenu), ParseMode.Markdown);
                log.info(logInfo);
                return;
            }
            if (currentUser.getSelectedShelterId() != 0) {
                telegramBotService.sendMessage(chatId, "Выберите, что вы хотите узнать о приюте:", markupHelper.buildMenu(mainMenuWithoutChose), ParseMode.Markdown);
                log.info(logInfo);
                return;
            }

            telegramBotService.sendMessage(chatId, "Выберите приют:", markupHelper.buildMenu(mainMenu), null);
            log.info(logInfo);
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
            newUser.setRole(UserRole.USER);
            newUser.setPhoneNumber(null);
            userService.addUser(newUser);
            log.info("User Registered: {}", newUser);
        } catch (Exception e) {
            log.error(e.getMessage() + "Error registering a new User");
        }
    }

}
