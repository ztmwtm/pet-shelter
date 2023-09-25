package com.example.petshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

public class TelegramBotServiceTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private Logger logger;

    @Mock
    private SendResponse sendResponse;

    @Mock
    private Message message;

    private TelegramBotService botService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        botService = new TelegramBotService(telegramBot, logger);
    }

    @Test
    public void testSendMessage() {
        when(telegramBot.execute(any(SendMessage.class))).thenReturn(sendResponse);

        botService.sendMessage(123456L, "Test message", null, null);

        verify(telegramBot).execute(any(SendMessage.class));
        verify(logger).info("SendMessage TelegramBotService");
    }

    @Test
    public void testSendPDFDocument() {
        when(telegramBot.execute(any(SendDocument.class))).thenReturn(sendResponse);
        when(sendResponse.isOk()).thenReturn(true);
        when(sendResponse.message()).thenReturn(message);

        botService.sendPDFDocument(123456L, "test.pdf", "PDF Document", null, null);

        verify(telegramBot).execute(any(SendDocument.class));
        verify(logger).info(anyString());
    }

    @Test
    public void testSendPicture() {
        when(telegramBot.execute(any(SendPhoto.class))).thenReturn(sendResponse);
        when(sendResponse.isOk()).thenReturn(true);
        when(sendResponse.message()).thenReturn(message);

        botService.sendPicture(123456L, "test.jpg", "Picture", null, null);

        verify(telegramBot).execute(any(SendPhoto.class));
        verify(logger).info(anyString());
    }

    @Test
    public void testSendPictureWithoutKeyboardAndParseMode() {
        when(telegramBot.execute(any(SendPhoto.class))).thenReturn(sendResponse);
        when(sendResponse.isOk()).thenReturn(true);
        when(sendResponse.message()).thenReturn(message);

        botService.sendPicture(123456L, "test.jpg", "Picture");

        verify(telegramBot).execute(any(SendPhoto.class));
        verify(logger).info(anyString());
    }

    @Test
    public void testSendDocumentWithoutCaptionAndParseMode() {
        when(telegramBot.execute(any(SendDocument.class))).thenReturn(sendResponse);
        when(sendResponse.isOk()).thenReturn(true);
        when(sendResponse.message()).thenReturn(message);

        botService.sendPDFDocument(123456L, "test.pdf", null, null, null);

        verify(telegramBot).execute(any(SendDocument.class));
        verify(logger).info(anyString());
    }
}