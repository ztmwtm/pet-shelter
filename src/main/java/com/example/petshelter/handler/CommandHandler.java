package com.example.petshelter.handler;

import com.example.petshelter.entity.Pet;
import com.example.petshelter.entity.UserReport;
import com.example.petshelter.entity.UserReportPhoto;
import com.example.petshelter.helper.MarkupHelper;
import com.example.petshelter.service.*;
import com.example.petshelter.type.PetStatus;
import com.example.petshelter.type.UserRole;
import com.example.petshelter.util.CallbackData;
import com.example.petshelter.util.Command;
import com.example.petshelter.util.Menu;
import com.example.petshelter.util.UserReportStatus;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ParseMode;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

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
    private final Map<String, String> volunteerCheckReportsMenu = new LinkedHashMap<>();

    /*
     * Нестатический блок инициализации метода и кнопок
     */ {
        commandExecutors.put(Command.START, this::handleStart);
        mainMenu.put(CallbackData.CATS.getTitle(), "\uD83D\uDC08 Приют для кошек");
        mainMenu.put(CallbackData.DOGS.getTitle(), "\uD83D\uDC15 Приют для собак");

        startVolunteer.put(CallbackData.START_VOLUNTEER.getTitle(), CallbackData.START_VOLUNTEER.getDescription());
        volunteerCheckReportsMenu.put(CallbackData.ACCEPT_REPORT.getTitle(), CallbackData.ACCEPT_REPORT.getDescription());
        volunteerCheckReportsMenu.put(CallbackData.REJECT_REPORT.getTitle(), CallbackData.REJECT_REPORT.getDescription());

        mainMenuWithoutChose.put(CallbackData.CATS_INFO.getTitle(), CallbackData.CATS_INFO.getDescription());
        mainMenuWithoutChose.put(CallbackData.CATS_TAKE.getTitle(), CallbackData.CATS_TAKE.getDescription());
        mainMenuWithoutChose.put(CallbackData.REPORT.getTitle(), CallbackData.REPORT.getDescription());
        mainMenuWithoutChose.put(CallbackData.HELP.getTitle(), CallbackData.HELP.getDescription());
        mainMenuWithoutChose.put(CallbackData.RESET_SHELTER.getTitle(), CallbackData.RESET_SHELTER.getDescription());

    }

    public CommandHandler(final TelegramBotService telegramBotService,
                          final UserService userService,
                          final UserReportService userReportService,
                          final UserReportPhotoService userReportPhotoService,
                          final PetService petService,
                          final MarkupHelper markupHelper) {
        this.telegramBotService = telegramBotService;
        this.userService = userService;
        this.userReportService = userReportService;
        this.userReportPhotoService = userReportPhotoService;
        this.petService = petService;
        this.markupHelper = markupHelper;
        log.info("Constructor CommandHandler");
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void handle(User user, Chat chat, String commandText) {
        try {

            Long chatId = chat.id();

            Command[] commands = Command.values();
            for (Command command : commands) {
                if (("/" + command.getTitle()).equals(commandText)) {
                    commandExecutors.get(command).accept(user, chat);
                    break;
                }
            }

            com.example.petshelter.entity.User thisUser = userService.getUserByChatId(chatId);
            UserReport thisReport = userReportService.getUserReportByUserIdAndStatusCreated(thisUser.getId());

            if (commandText.startsWith("/pet")) {
                Long petId = Long.valueOf(commandText.replace("/pet", ""));
                petService.changePetStatus(petId, PetStatus.CHOSEN);
                log.info("Chosen Pet Id {}", petId);
                String text = getListOfAvailableAdopters();
                telegramBotService.sendMessage(chatId, text);
                return;
            }

            if (commandText.startsWith("/user")) {
                Long adopterId = Long.valueOf(commandText.replace("/user", ""));
                List<Pet> pets = petService.getPetsWithStatus(PetStatus.CHOSEN);
                Long petId = pets.get(0).getId();
                com.example.petshelter.entity.User adopter = userService.getUserById(adopterId);
                petService.makePetAdopted(petId, adopter, PetStatus.ADOPTED);
                userService.updateUserRoleByUserId(adopterId, UserRole.ADOPTER);
                String text = "Готово! Животное выдано пользователю.";
                telegramBotService.sendMessage(chatId, text);

                return;
            }

            if (commandText.startsWith("/adoptedPet")) {
                Long petId = Long.valueOf(commandText.replace("/adoptedPet", ""));
                Pet thisPet = petService.getPetById(petId);

                thisReport.setPet(thisPet);
                userReportService.updateUserReport(thisReport.getId(), thisReport);

                log.info("Handle CommandHandler - Select Adopted Pet");

                String text = "Теперь пришлите, пожалуйста, фото питомца";
                telegramBotService.sendMessage(chatId, text);

                return;
            }

            if (commandText.startsWith("/reportsForValidation")) {
                Long reportId = Long.valueOf(commandText.replace("/reportsForValidation", ""));
                UserReport thisUserReport = userReportService.getUserReportById(reportId);

                thisUser.setActiveReportForChecking(reportId);
                userService.updateUser(thisUser.getId(), thisUser);

                log.info("Handle CommandHandler - Select User report for validation");

                StringBuilder builder = new StringBuilder();
                builder
                        .append("*Id отчета:* \n")
                        .append(thisUserReport.getId())
                        .append("\n \n")
                        .append("*Дата создания отчета:* \n")
                        .append(thisUserReport.getDateOfCreation())
                        .append("\n \n")
                        .append("*Питомец:* \n")
                        .append(thisUserReport.getPet().getNickname())
                        .append("\n \n")
                        .append("*Усыновитель:* \n")
                        .append(thisUserReport.getUser().getFirstName())
                        .append(" ")
                        .append(thisUserReport.getUser().getLastName())
                        .append("\n \n")
                        .append("*Диета:* \n")
                        .append(thisUserReport.getPetDiet())
                        .append("\n \n")
                        .append("*Состояние здоровья:* \n")
                        .append(thisUserReport.getHealth())
                        .append("\n \n")
                        .append("*Поведение и привычки:* \n")
                        .append(thisUserReport.getBehavior())
                        .append("\n \n")
                        .append("*Фото питомца:* \n")
                        .append("\n");

                String text = builder.toString();

                telegramBotService.sendMessage(chatId, text, null, ParseMode.Markdown);

                UserReportPhoto photo = userReportPhotoService.findUserReportPhoto(thisUserReport.getId());
                telegramBotService.sendPhoto(chat.id(), new File(photo.getFilePath()));

                String txt = "Проверка отчета";
                telegramBotService.sendMessage(chat.id(), txt, markupHelper.buildMenu(volunteerCheckReportsMenu), ParseMode.Markdown);

                return;
            }

            if (thisUser.getActiveMenu().equals(Menu.REPORT)) {

                UserReportPhoto thisReportPhoto = userReportPhotoService.findUserReportPhoto(thisReport.getId());

                if (thisReportPhoto == null) {

                    String text = "Теперь пришлите, пожалуйста, фото питомца";
                    telegramBotService.sendMessage(chatId, text);

                    return;
                }

                if (thisReport.getPetDiet() == null) {
                    thisReport.setPetDiet(commandText);
                    userReportService.updateUserReport(thisReport.getId(), thisReport);

                    log.info("Handle CommandHandler - Add Pet Diet");

                    String text = "Отлично! Информация добавлена в отчет. Теперь пришлите, пожалуйста, в текстовом сообщении описание общего самочувствия питомца и особенностей привыкания к новому месту";
                    telegramBotService.sendMessage(chatId, text);

                    return;
                }

                if (thisReport.getHealth() == null) {
                    thisReport.setHealth(commandText);
                    userReportService.updateUserReport(thisReport.getId(), thisReport);

                    log.info("Handle CommandHandler - Add Pet Health");

                    String text = "Отлично! Информация добавлена в отчет. Теперь пришлите, пожалуйста, в текстовом сообщении особенности поведения питомца: отказ от старых привычек, приобретение новых";
                    telegramBotService.sendMessage(chatId, text);

                    return;
                }

                if (thisReport.getBehavior() == null) {
                    thisReport.setBehavior(commandText);
                    userReportService.updateUserReport(thisReport.getId(), thisReport);

                    log.info("Handle CommandHandler- Add Pet Behavior");

                    String text = "Отлично! Вы предоставили всю необходимую информацию для отчета. Отчет отправляется на проверку волонтеру";
                    telegramBotService.sendMessage(chatId, text);

                    thisReport.setStatus(UserReportStatus.ON_VERIFICATION);
                    userReportService.updateUserReport(thisReport.getId(), thisReport);

                    log.info("Handle CommandHandler - User report on verification");
                    return;
                }
            }
            log.info("Handle CommandHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error Handle CommandHandler");
        }
    }

    @NotNull
    private String getListOfAvailableAdopters() {
        String text;
        List<com.example.petshelter.entity.User> availableAdopters = userService.getUsersAvailableForAdopt();
        if (availableAdopters.isEmpty()) {
            List<Pet> pets = petService.getPetsWithStatus(PetStatus.CHOSEN);
            pets.forEach(pet -> petService.changePetStatus(pet.getId(), PetStatus.AVAILABLE));
            text = "Усыновителей, к сожалению, не найдено";
        } else {
            StringBuilder builder = new StringBuilder();
            availableAdopters.forEach(adopter -> builder.append(adopter.getId())
                    .append(". ")
                    .append(adopter.getFirstName())
                    .append(", ")
                    .append(adopter.getTgUsername())
                    .append(" => /user")
                    .append(adopter.getId())
                    .append("\n"));
            text = "Выберите пользователя, кликнув по нужной ссылке:\n" + builder;
        }

        return text;
    }

    @NotNull
    public String getListOfPetsForAdopter(Long userId) {
        String text;
        List<Pet> pets = petService.getPetsForAdopter(userId);
        if (pets.isEmpty()) {
            text = "Питомцы, к сожалению, не найдены";
        } else {
            StringBuilder builder = new StringBuilder();
            pets.forEach(pet -> builder.append(pet.getId())
                    .append(". ")
                    .append(pet.getNickname())
                    .append(" => /adoptedPet")
                    .append(pet.getId())
                    .append("\n"));
            text = "Выберите питомца, кликнув по нужной ссылке:\n" + builder;
        }

        return text;
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

                com.example.petshelter.entity.User thisUser = userService.getUserByChatId(chatId);
                thisUser.setActiveMenu(Menu.MAIN_MENU);
                userService.updateUser(thisUser.getId(),thisUser);

                log.info(logInfo);
                return;
            }
            if (currentUser.getRole() == UserRole.VOLUNTEER) {
                telegramBotService.sendMessage(chatId, GREETING_VOLUNTEER, markupHelper.buildMenu(startVolunteer), ParseMode.Markdown);
                currentUser.setActiveMenu(Menu.START_VOLUNTEER);
                userService.updateUser(currentUser.getId(),currentUser);
                log.info(logInfo);
                return;
            }
            if (currentUser.getSelectedShelterId() != 0) {
                telegramBotService.sendMessage(chatId, "Выберите что вы хотите узнать о приюте:", markupHelper.buildMenu(mainMenuWithoutChose), ParseMode.Markdown);
                currentUser.setActiveMenu(Menu.MAIN_MENU_WITHOUT_CHOSE);
                userService.updateUser(currentUser.getId(),currentUser);
                log.info(logInfo);
                return;
            }
            telegramBotService.sendMessage(chatId, "Выберите приют:", markupHelper.buildMenu(mainMenu), null);
            currentUser.setActiveMenu(Menu.MAIN_MENU);
            userService.updateUser(currentUser.getId(),currentUser);
            log.info(logInfo);
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleStart CommandHandler");
        }
    }


}
