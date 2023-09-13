package com.example.petshelter.handler;

import com.example.petshelter.helper.MarkupHelper;
import com.example.petshelter.repository.UserRepository;
import com.example.petshelter.service.ShelterService;
import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.util.CallbackData;
import com.example.petshelter.util.UserRole;
import com.example.petshelter.util.Volunteer;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BiConsumer;

@Slf4j
@Component
public class VolonteerHandler {
    private final Map<Volunteer, BiConsumer<User, Chat>> queryExecutors = new EnumMap<>(Volunteer.class);
    private final TelegramBotService telegramBotService;
    private final ShelterService shelterService;
    private final MarkupHelper markupHelper;
    private UserRepository userRepository;
    private final User volunteer = userRepository.findUserByRole(UserRole.VOLUNTEER);

    private final Map<String, String> volonteerMenu = new LinkedHashMap<>();

    {
//        добавить кнопки
    }
    public VolonteerHandler(TelegramBotService telegramBotService, ShelterService shelterService, MarkupHelper markupHelper, UserRepository userRepository) {
        this.telegramBotService = telegramBotService;
        this.shelterService = shelterService;
        this.markupHelper = markupHelper;
        this.userRepository = userRepository;
    }

    public void handle(User user, Chat chat, String data) {
        try {
            CallbackData[] queries = CallbackData.values();
            for (CallbackData query : queries) {
                if (Objects.equals(query.getTitle(), data)) {
                    queryExecutors.get(query).accept(user, chat);
                    break;
                }
            }
            log.info("Handle CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error Handle CallbackQueryHandler");
        }
    }

    public void VolunteerHelp(User user, Chat userChat) {
        try {
            String text = "Ответьте на вопросы пользователя @" + userChat.username();
            telegramBotService.sendMessage(volunteer.id(), text, null, null);
        }catch (Exception e){
            log.error(e.getMessage() + "Error handleVolunteerHelp CallbackQueryHandler");
        }
    }
}
