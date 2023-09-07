package com.example.petshelter.handler;

import com.example.petshelter.helper.MarkupHelper;
import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.util.CallbackData;
import com.example.petshelter.util.Command;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ParseMode;
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
    private final MarkupHelper markupHelper;
    private final Map<String, String> mainMenu = new HashMap<>();

    /**
     * Нестатический блок инициализации метода и кнопок
     */
    {
        commandExecutors.put(Command.START, this::handleStart);
        mainMenu.put(CallbackData.CATS.getTitle(), "Приют для кошек");
        mainMenu.put(CallbackData.DOGS.getTitle(), "Приют для собак");
    }

    @Autowired
    public CommandHandler(final TelegramBotService telegramBotService, final MarkupHelper markupHelper) {
        this.telegramBotService = telegramBotService;
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
            log.info("Hendle CommandHendler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error Hendle CommandHendler");
        }
    }

    /**
     * Метод отвечающий за приветствие пользователя в начале работы
     * @param user
     * @param chat
     */
    private void handleStart(User user, Chat chat) {
        try {
            Long chatId = chat.id();
            String userName = user.firstName();
            telegramBotService.sendMessage(chatId, userName + GREETING, markupHelper.buildMenu(mainMenu), null);
            log.info("HendelStart CommandHendel");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HendleStart CommandHendler");
        }
    }
}
