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

@Slf4j
@Component
public class CommandHandler {

    private static final String GREETING = """
            , привет!
            Я - бот-помощник приюта домашних животных.
            Начните с выбора приюта:""";
    private final Map<Command, BiConsumer<User, Chat>> commandExecutors = new HashMap<>();
    private final TelegramBotService telegramBotService;
    private final MarkupHelper markupHelper;
    private final Map<String, String> mainMenu = new HashMap<>();

    {
        commandExecutors.put(Command.START, this::handleStart);
        mainMenu.put(CallbackData.CATS.getTitle(), "Приют для кошек");
        mainMenu.put(CallbackData.DOGS.getTitle(), "Приют для собак");
    }

    @Autowired
    public CommandHandler(final TelegramBotService telegramBotService, final MarkupHelper markupHelper) {
        this.telegramBotService = telegramBotService;
        this.markupHelper = markupHelper;
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
            log.error(e.getMessage() + "Error Handel CommandHandler");
        }
    }

    private void handleStart(User user, Chat chat) {
        try {
            Long chatId = chat.id();
            String userName = user.firstName();

            // TODO
        /*if (userRepository.findUserByChatId(chatId) == null) {
            registerUser(chatId, user);
            String greeting = userName + GREETING;
            telegramBotService.sendMessage(chatId, greeting, markupHelper.buildMenu(mainMenu), ParseMode.Markdown);
        } else {
            telegramBotService.sendMessage(chatId, "", markupHelper.buildMenu(mainMenu), null);
        }*/

            telegramBotService.sendMessage(chatId, userName + GREETING, markupHelper.buildMenu(mainMenu), null);
            log.info("HandleStart CommandHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HendleStart CommandHandler");
        }
    }

}
