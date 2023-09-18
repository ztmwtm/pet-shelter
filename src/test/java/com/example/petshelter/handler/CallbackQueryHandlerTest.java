package com.example.petshelter.handler;

import com.example.petshelter.listener.TelegramBotUpdatesListener;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CallbackQueryHandlerTest {

    private final TelegramBot telegramBot = Mockito.mock(TelegramBot.class);
    private final CommandHandler commandHandler = Mockito.mock(CommandHandler.class);
    private final ContactHandler contactHandler = Mockito.mock(ContactHandler.class);
    private final CallbackQueryHandler callbackQueryHandler = Mockito.mock(CallbackQueryHandler.class);

    @InjectMocks
    private final UpdateHandler updateHandler = new UpdateHandler(
            commandHandler,
            contactHandler,
            callbackQueryHandler
    );

    @InjectMocks
    private TelegramBotUpdatesListener listener = new TelegramBotUpdatesListener(
            telegramBot,
            updateHandler
    );

    @BeforeEach
    public void beforeEach() {
        when(telegramBot.execute(any()))
                .thenReturn(
                        BotUtils.fromJson("{\"ok\": true}",
                                SendResponse.class
                        ));
    }

    @Test
    void handle() throws URISyntaxException, IOException {
        String json = Files.readString(
                Paths.get(
                        Objects.requireNonNull(
                                CallbackQueryHandlerTest.class.getResource("callback_query_update.json")
                        ).toURI()
                )
        );
        Update update = getUpdate(json, "/cats");
        listener.process(Collections.singletonList(update));

        ArgumentCaptor<BaseRequest<SetMyCommands, BaseResponse>> argumentCaptor = ArgumentCaptor.forClass(BaseRequest.class);
        verify(telegramBot).execute(argumentCaptor.capture());

        verify(callbackQueryHandler).handle(
                update.callbackQuery().from(),
                update.callbackQuery().message().chat(),
                update.callbackQuery().data()
        );
    }

    private Update getUpdate(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%button_text%", replaced), Update.class);
    }

}