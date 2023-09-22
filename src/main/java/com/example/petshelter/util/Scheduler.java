package com.example.petshelter.util;

import com.example.petshelter.entity.Pet;
import com.example.petshelter.service.PetService;
import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class Scheduler {
    private static final int APPROX_VALUE = 5;
    private final TelegramBotService telegramBotService;
    private final PetService petService;
    private final UserService userService;

    private final static String CONGRATULATION_OF_ADOPTION = "Уважаемый %s поздравляем вас с окончательным усыновлением %s\n" +
            "Теперь вы полноправный владелец, окружите вашего питомца заботой и любовью, а он ответит вам взаимностью.";

    public Scheduler(TelegramBotService telegramBotService, PetService petService) {
        this.telegramBotService = telegramBotService;
        this.petService = petService;
    }

    @Scheduled(cron = "0 0 22 * * *")
    public void reportNotification() {

    }

    @Scheduled(cron = "0 0 12 * * *")
    public void sendAdoptCongratulationNotification() {
//        List<Pet> recipients = petService.getAllPets().stream() //TODO Не самое видимо эффективное решение перебирать всю базу. Можно реализовать в репе
//                .filter(pet -> pet.getDayOfAdopt().isEqual(LocalDate.now().minusDays(pet.getDaysToAdaptation())))
//                .toList();
//
//        recipients.forEach(pet -> telegramBotService.sendPicture(pet.getAdopter().getChatId(),
//                "storage/congratulations.jpg",
//                String.format(CONGRATULATION_OF_ADOPTION, pet.getAdopter().getFirstName(), pet.getNickname())));
    }

    private boolean compareTimeWithCurrentTime(LocalTime time) {
        return LocalTime.now().getHour() == time.getHour() &&
                LocalTime.now().getMinute() == time.getMinute() &&
                Math.abs(LocalTime.now().getSecond() - time.getSecond()) <= APPROX_VALUE;
    }
}
