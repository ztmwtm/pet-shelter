package com.example.petshelter.handler;

import com.example.petshelter.entity.Pet;
import com.example.petshelter.entity.UserReport;
import com.example.petshelter.entity.UserReportPhoto;
import com.example.petshelter.helper.MarkupHelper;
import com.example.petshelter.service.*;
import com.example.petshelter.util.CallbackData;
import com.example.petshelter.util.Command;
import com.example.petshelter.util.UserReportStatus;
import com.example.petshelter.util.UserRole;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ParseMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static java.lang.Long.parseLong;

/**
 * Класс отвечающий за приветствие пользователя и предлагающий выбор приюта
 */
@Slf4j
@Component
public class CommandHandler {

    /**
     * Текст выводящийся в приветствии
     */
    private static final String GREETING = """
            , привет!
            Я - бот-помощник приюта домашних животных.
            Начните с выбора приюта:""";
    private static final String GREETING_VOLUNTEER = """
            Привет, волонтер!
            Я ловлю для тебя сообщения. А сейчас можешь сделать свои дела
            """;
    private final Map<Command, BiConsumer<User, Chat>> commandExecutors = new EnumMap<>(Command.class);
    private final TelegramBotService telegramBotService;
    private final UserService userService;
    private final UserReportService userReportService;
    private final UserReportPhotoService userReportPhotoService;
    private final PetService petService;
    private final MarkupHelper markupHelper;
    private final Map<String, String> mainMenu = new LinkedHashMap<>();
    private final Map<String, String> startVolunteer = new LinkedHashMap<>();
    private final Map<String, String> mainMenuWithoutChose = new LinkedHashMap<>();

    /*
     * Нестатический блок инициализации метода и кнопок
     */ {
        commandExecutors.put(Command.START, this::handleStart);
        mainMenu.put(CallbackData.CATS.getTitle(), "\uD83D\uDC08 Приют для кошек");
        mainMenu.put(CallbackData.DOGS.getTitle(), "\uD83D\uDC15 Приют для собак");
        startVolunteer.put(CallbackData.START_VOLUNTEER.getTitle(), CallbackData.START_VOLUNTEER.getDescription());

        mainMenuWithoutChose.put(CallbackData.CATS_INFO.getTitle(), CallbackData.CATS_INFO.getDescription());
        mainMenuWithoutChose.put(CallbackData.CATS_TAKE.getTitle(), CallbackData.CATS_TAKE.getDescription());
        mainMenuWithoutChose.put(CallbackData.REPORT.getTitle(), CallbackData.REPORT.getDescription());
        mainMenuWithoutChose.put(CallbackData.HELP.getTitle(), CallbackData.HELP.getDescription());
        mainMenuWithoutChose.put(CallbackData.RESET_SHELTER.getTitle(), CallbackData.RESET_SHELTER.getDescription());

    }

    @Autowired
    public CommandHandler(final TelegramBotService telegramBotService,
                          final UserService userService,
                          UserReportService userReportService, UserReportPhotoService userReportPhotoService, PetService petService, final MarkupHelper markupHelper) {
        this.telegramBotService = telegramBotService;
        this.userService = userService;
        this.userReportService = userReportService;
        this.userReportPhotoService = userReportPhotoService;
        this.petService = petService;
        this.markupHelper = markupHelper;
        log.info("Constructor CommandHandler");
    }

    public void handle(User user, Chat chat, String commandText) {
        try {

            Long chatId = chat.id();
            Long userId = user.id();

            Command[] commands = Command.values();
            for (Command command : commands) {
                if (("/" + command.getTitle()).equals(commandText)) {
                    commandExecutors.get(command).accept(user, chat);
                    UpdateHandler.activeMenu = "";
                    break;
                }
            }

            if ("handleReport".equals(UpdateHandler.activeMenu)) {

                com.example.petshelter.entity.User thisUser = userService.getUserByChatId(userId);
                UserReport thisReport = userReportService.getUserReportByUserIdAndStatusCreated(thisUser.getId());
                UserReportPhoto thisReportPhoto = userReportPhotoService.findUserReportPhoto(thisReport.getId());

                if(thisReport.getPet() == null) {

                    Pet thisPet = petService.getPetById(parseLong(commandText));

                    thisReport.setPet(thisPet);
                    userReportService.updateUserReport(thisReport.getId(), thisReport);

                    log.info("Handle CommandHandler - Add Pet Id");

                    String text = "Теперь пришлите, пожалуйста, фото питомца";
                    telegramBotService.sendMessage(chatId, text, null, ParseMode.Markdown);

                    return;
                }

                if (thisReportPhoto == null){

                    String text = "Теперь пришлите, пожалуйста, фото питомца";
                    telegramBotService.sendMessage(chatId, text, null, ParseMode.Markdown);

                    return;
                }

                if (thisReport.getPetDiet() == null){
                    thisReport.setPetDiet(commandText);
                    userReportService.updateUserReport(thisReport.getId(), thisReport);

                    log.info("Handle CommandHandler - Add Pet Diet");

                    String text = "Отлично! Информация добавлена в отчет. Теперь пришлите, пожалуйста, в текстовом сообщении описание общего самочувствия питомца и особенности привыкания к новому месту";
                    telegramBotService.sendMessage(chatId, text, null, ParseMode.Markdown);

                    return;
                }

                if (thisReport.getHealth() == null){
                    thisReport.setHealth(commandText);
                    userReportService.updateUserReport(thisReport.getId(), thisReport);

                    log.info("Handle CommandHandler - Add Pet Health");

                    String text = "Отлично! Информация добавлена в отчет. Теперь пришлите, пожалуйста, в текстовом сообщении особенности поведения питомца: отказ от старых привычек, приобретение новых";
                    telegramBotService.sendMessage(chatId, text, null, ParseMode.Markdown);

                    return;
                }

                if (thisReport.getBehavior() == null){
                    thisReport.setBehavior(commandText);
                    userReportService.updateUserReport(thisReport.getId(), thisReport);

                    log.info("Handle CommandHandler- Add Pet Behavior");

                    String text = "Отлично! Вы предоставили всю необходимую информацию для отчета. Отчет отправляется на проверку волонтеру";
                    telegramBotService.sendMessage(chatId, text, null, ParseMode.Markdown);

                    thisReport.setStatus(UserReportStatus.ON_VERIFICATION);
                    userReportService.updateUserReport(thisReport.getId(), thisReport);

                    UpdateHandler.activeMenu = "";

                    log.info("Handle CommandHandler - User report on verification");
                    return;
                }
            }
            log.info("Handle CommandHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error Handle CommandHandler");
        }
    }

    /**
     * Метод отвечающий за приветствие пользователя в начале работы
     *
     * @param user User
     * @param chat Chat
     */
    private void handleStart(User user, Chat chat) {
        try {
            Long chatId = chat.id();
            com.example.petshelter.entity.User currentUser = userService.getUserByChatId(chatId);
            String logInfo = "HandleStart CommandHandler";
            if (currentUser == null) {
                String userName = user.firstName();
                userService.registerNewUser(user, chatId);
                log.info("User registered successfully");
                telegramBotService.sendMessage(chatId, userName + GREETING, markupHelper.buildMenu(mainMenu), ParseMode.Markdown);
                log.info(logInfo);
                return;
            }
            if (currentUser.getRole() == UserRole.VOLUNTEER) {
                telegramBotService.sendMessage(chatId, GREETING_VOLUNTEER, markupHelper.buildMenu(startVolunteer), ParseMode.Markdown);
                log.info(logInfo);
                return;
            }
            if (currentUser.getSelectedShelterId() != 0) {
                telegramBotService.sendMessage(chatId, "Выберите что вы хотите узнать о приюте:", markupHelper.buildMenu(mainMenuWithoutChose), ParseMode.Markdown);
                log.info(logInfo);
                return;
            }
            telegramBotService.sendMessage(chatId, "Выберите приют:", markupHelper.buildMenu(mainMenu), null);
            log.info(logInfo);
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleStart CommandHandler");
        }
    }


}
