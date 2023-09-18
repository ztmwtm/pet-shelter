package com.example.petshelter.handler;

import com.example.petshelter.listener.TelegramBotUpdatesListener;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateHandlerTest {

    private final TelegramBot telegramBot = Mockito.mock(TelegramBot.class);
    private final CommandHandler commandHandler = Mockito.mock(CommandHandler.class);
    private final ContactHandler contactHandler = Mockito.mock(ContactHandler.class);
    private final CallbackQueryHandler callbackQueryHandler = Mockito.mock(CallbackQueryHandler.class);
    private final PhotoUploadHandler photoUploadHandler = Mockito.mock(PhotoUploadHandler.class);

    @InjectMocks
    private final UpdateHandler updateHandler = new UpdateHandler(
            commandHandler,
            contactHandler,
            callbackQueryHandler,
            photoUploadHandler
    );

    @InjectMocks
    private TelegramBotUpdatesListener listener = new TelegramBotUpdatesListener(
            telegramBot,
            updateHandler
    );

    @Test
    void handle() {
        Update update = new Update();
        listener.process(Collections.singletonList(update));

        ArgumentCaptor<BaseRequest<SetMyCommands, BaseResponse>> argumentCaptor = ArgumentCaptor.forClass(BaseRequest.class);

        verify(telegramBot).execute(argumentCaptor.capture());
        verify(telegramBot, times(1))
                .execute(argumentCaptor.capture());
    }

}