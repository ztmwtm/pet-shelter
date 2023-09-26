package com.example.petshelter.util;

import com.example.petshelter.entity.UserReport;
import com.example.petshelter.service.*;
import com.pengrad.telegrambot.model.request.ParseMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class Scheduler {
    private static final int APPROX_VALUE = 5;
    private final TelegramBotService telegramBotService;
    private final PetService petService;
    private final UserService userService;
    private final UserReportService userReportService;
    private final UserReportPhotoService userReportPhotoService;
    private final Logger logger = LoggerFactory.getLogger(UserReportService.class);

    private final static String CONGRATULATION_OF_ADOPTION = "Уважаемый %s поздравляем вас с окончательным усыновлением %s\n" +
            "Теперь вы полноправный владелец, окружите вашего питомца заботой и любовью, а он ответит вам взаимностью.";

    public Scheduler(TelegramBotService telegramBotService, PetService petService, UserService userService, UserReportService userReportService, UserReportPhotoService userReportPhotoService) {
        this.telegramBotService = telegramBotService;
        this.petService = petService;
        this.userService = userService;
        this.userReportService = userReportService;
        this.userReportPhotoService = userReportPhotoService;
    }

    @Scheduled(cron = "0 0 22 * * *")
    public void reportNotification() {

    }

    @Scheduled(cron = "0 0 20 * * *")
    public void reportCompleteCheckingNotification() {
        logger.info("Was called scheduled method to check completion of User Report");

        List<UserReport> reports = userReportService.getUserReportByStatus(UserReportStatus.CREATED);

        String text = "";

        for (UserReport report : reports) {

            if (report.getPet() == null) {
                text = "Напоминаем, что у вас есть незавершенный отчет. Продолжите заполнять отчет, перейдя в соответствующее меню и указав ID питомца";
            }

            if (report.getPet() != null && userReportPhotoService.findUserReportPhoto(report.getId()) == null) {
                text = "Напоминаем, что у вас есть незавершенный отчет. Продолжите заполнять отчет, перейдя в соответствующее меню и прикрепив фото питомца";
            }

            if (report.getPet() != null
                    && userReportPhotoService.findUserReportPhoto(report.getId()) != null
                    && report.getPetDiet() == null
            ) {
                text = "Напоминаем, что у вас есть незавершенный отчет. Продолжите заполнять отчет, перейдя в соответствующее меню и прислав в текстовом сообщении описание рациона питомца";
            }

            if (report.getPet() != null
                    && userReportPhotoService.findUserReportPhoto(report.getId()) != null
                    && report.getPetDiet() != null
                    && report.getHealth() == null
            ) {
                text = "Напоминаем, что у вас есть незавершенный отчет. Продолжите заполнять отчет, перейдя в соответствующее меню и прислав в текстовом сообщении описание общего самочувствия питомца и особенностей привыкания к новому месту";
            }

            if (report.getPet() != null
                    && userReportPhotoService.findUserReportPhoto(report.getId()) != null
                    && report.getPetDiet() != null
                    && report.getHealth() != null
                    && report.getBehavior() == null
            ) {
                text = "Напоминаем, что у вас есть незавершенный отчет. Продолжите заполнять отчет, перейдя в соответствующее меню и прислав в текстовом сообщении описание оповедения питомца: отказ от старых привычек, приобретение новых";
            }

            telegramBotService.sendMessage(report.getUser().getChatId(), text, null, ParseMode.Markdown);
        }

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
