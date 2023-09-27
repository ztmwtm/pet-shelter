package com.example.petshelter.util;

import com.example.petshelter.entity.Pet;
import com.example.petshelter.entity.UserReport;
import com.example.petshelter.service.*;
import com.example.petshelter.type.PetStatus;
import com.pengrad.telegrambot.model.request.ParseMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Scheduler {
    private final TelegramBotService telegramBotService;
    private final PetService petService;
    private final UserService userService;
    private final UserReportService userReportService;
    private final UserReportPhotoService userReportPhotoService;
    private final Logger logger = LoggerFactory.getLogger(Scheduler.class);

    public Scheduler(TelegramBotService telegramBotService, PetService petService, UserService userService, UserReportService userReportService, UserReportPhotoService userReportPhotoService) {
        this.telegramBotService = telegramBotService;
        this.petService = petService;
        this.userService = userService;
        this.userReportService = userReportService;
        this.userReportPhotoService = userReportPhotoService;
    }

    @Scheduled(cron = "0 0 22 * * *")
    public void congratulationOfAdoption() {

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

    @Scheduled(cron = "0 0 10-22 * * *")
    public void sendAdoptCongratulationNotification() {
        List<Long> readyToFinalAdoptPetsId = petService.getPetsReadyToFinalAdopt();
        List<Pet> recipients = petService.getAllPets().stream()
                .filter(pet -> readyToFinalAdoptPetsId.contains(pet.getId()))
                .toList();
        recipients.forEach(pet -> telegramBotService.sendMessage(pet.getAdopter().getChatId(), Templates.getCongratulationText(pet.getAdopter())));
        recipients.forEach(pet -> petService.changePetStatus(pet.getId(), PetStatus.ADOPTED));
    }
}
