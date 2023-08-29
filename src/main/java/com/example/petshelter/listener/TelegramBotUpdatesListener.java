package com.example.petshelter.listener;

import com.example.petshelter.service.TelegramBotService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBotService telegramBotService;
    private final TelegramBot telegramBot;
    private static final String GREETING = """
             , привет!
            Я - бот-помощник приюта домашних животных.
            Начните с выбора приюта:""";

    @Autowired
    public TelegramBotUpdatesListener(final TelegramBotService telegramBotService,
                                      @NotNull final TelegramBot telegramBot) {
        this.telegramBotService = telegramBotService;
        this.telegramBot = telegramBot;

        BaseResponse response = telegramBot.execute(new SetMyCommands(
                new BotCommand("/start", "Начало работы"),
                new BotCommand("/help", "Позвать волонтера")
        ));
        // TODO: logger response
        System.out.println(response);
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(@NotNull List<Update> updates) {
        updates.forEach(update -> {
            if (update.message() != null) {
                Message message = update.message();
                Long chatId = message.chat().id();
                String text = message.text();
                System.out.println(text);
                if ("/start".equals(text)) {
                    // TODO: registerUser()
                    String userName = message.chat().firstName();
                    String greeting = userName + GREETING;
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                            new InlineKeyboardButton("Приют для кошек").callbackData("cats"),
                            new InlineKeyboardButton("Приют для собак").callbackData("dogs")
                    );
                    telegramBotService.sendMessage(chatId, greeting, inlineKeyboard, null);
                } else {
                    telegramBotService.sendMessage(chatId, "Неверная команда");
                }
            } else if (update.callbackQuery() != null) {
                String query = update.callbackQuery().data();
                Long chatId = update.callbackQuery().message().chat().id();
                if ("cats".equals(query)) {
                    String text = "Выберите, что вы хотите узнать о приюте для кошек:";
                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                            new InlineKeyboardButton[]{
                                    new InlineKeyboardButton("Узнать информацию о приюте").callbackData("cats.info"),
                            },
                            new InlineKeyboardButton[]{
                                    new InlineKeyboardButton("Как взять животное из приюта").callbackData("cats.take"),
                            },
                            new InlineKeyboardButton[]{
                                    new InlineKeyboardButton("Прислать отчет о питомце").callbackData("cats.report"),
                            },
                            new InlineKeyboardButton[]{
                                    new InlineKeyboardButton("Позвать волонтера").callbackData("cats.volunteer"),
                            }
                    );
                    telegramBotService.sendMessage(chatId, text, inlineKeyboard, ParseMode.MarkdownV2);
                }
                if ("cats.info".equals(query)) {
                    String text = "Этап 1. Консультация с новым пользователем";
                    SendMessage sendMessage = new SendMessage(chatId, text);
                    telegramBot.execute(sendMessage);
                }
                if ("cats.take".equals(query)) {
                    String text = "Этап 2. Консультация с потенциальным хозяином животного из приюта";
                    SendMessage sendMessage = new SendMessage(chatId, text);
                    telegramBot.execute(sendMessage);
                }
                if ("cats.report".equals(query)) {
                    String text = "Этап 3. Ведение питомца";
                    SendMessage sendMessage = new SendMessage(chatId, text);
                    telegramBot.execute(sendMessage);
                }
                if ("cats.volunteer".equals(query)) {
                    String text = "Позвать волонтера";
                    SendMessage sendMessage = new SendMessage(chatId, text);
                    telegramBot.execute(sendMessage);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
