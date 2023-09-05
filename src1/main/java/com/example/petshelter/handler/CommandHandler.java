package com.example.petshelter.handler;

import com.example.petshelter.helper.MarkupHelper;
import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.util.CallbackData;
import com.example.petshelter.util.Command;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

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
    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("test/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(CommandHandler.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }

    {
        commandExecutors.put(Command.START, this::handleStart);
        mainMenu.put(CallbackData.CATS.getTitle(), "Приют для кошек");
        mainMenu.put(CallbackData.DOGS.getTitle(), "Приют для собак");
    }

    @Autowired
    public CommandHandler(final TelegramBotService telegramBotService, final MarkupHelper markupHelper) {
        this.telegramBotService = telegramBotService;
        this.markupHelper = markupHelper;
        LOGGER.log(Level.INFO, "Constructor CommandHendler");
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
            LOGGER.log(Level.INFO, "Handle CommandHandler");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error Handel CommandHandler", e);
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
            LOGGER.log(Level.INFO, "HandleStart CommandHandler");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error HendleStart CommandHandler", e);
        }
    }

}
