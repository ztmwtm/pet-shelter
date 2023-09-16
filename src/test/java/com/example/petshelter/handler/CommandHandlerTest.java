package com.example.petshelter.handler;

import com.example.petshelter.listener.TelegramBotUpdatesListener;
import com.example.petshelter.service.TelegramBotService;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CommandHandlerTest {

    private final TelegramBot telegramBot = Mockito.mock(TelegramBot.class);
    private final UpdateHandler updateHandler = Mockito.mock(UpdateHandler.class);

    @InjectMocks
    private TelegramBotUpdatesListener listener = new TelegramBotUpdatesListener(
            telegramBot,
            updateHandler
    );

    @Test
    void handle() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(
                        Objects.requireNonNull(
                                CommandHandlerTest.class.getResource("command_update.json")
                        ).toURI()
                )
        );
        Update update = getUpdate(json, "/start");
        listener.process(Collections.singletonList(update));

        ArgumentCaptor<SendMessage> argumentCaptor = ArgumentCaptor.forClass(SendMessage.class);
//        Mockito.verify(telegramBot).execute(argumentCaptor.capture());
        SendMessage actual = argumentCaptor.getValue();
        System.out.println( actual );
    }

    private Update getUpdate(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%text%", replaced), Update.class);
    }

}