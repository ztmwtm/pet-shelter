package com.example.petshelter.handler;

import com.example.petshelter.entity.Pet;
import com.example.petshelter.entity.Shelter;
import com.example.petshelter.entity.UserReport;
import com.example.petshelter.entity.UserReportPhoto;
import com.example.petshelter.helper.MarkupHelper;
import com.example.petshelter.service.*;
import com.example.petshelter.type.PetStatus;
import com.example.petshelter.type.PetType;
import com.example.petshelter.util.CallbackData;
import com.example.petshelter.util.Menu;
import com.example.petshelter.util.UserReportStatus;
import com.example.petshelter.util.Templates;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiConsumer;

import static com.example.petshelter.type.UserRole.ADOPTER;

/**
 * Класс отвечающий за обработку команд поступающих из чата
 */

@Slf4j
@Component
public class CallbackQueryHandler {

    private final Map<CallbackData, BiConsumer<User, Chat>> queryExecutors = new EnumMap<>(CallbackData.class);
    private final TelegramBotService telegramBotService;
    private final ShelterService shelterService;
    private final UserService userService;
    private final UserReportService userReportService;
    private final UserReportPhotoService userReportPhotoService;
    private final PetService petService;
    private final MarkupHelper markupHelper;
    private final CommandHandler commandHandler;
    private final Map<String, String> catsMenu = new LinkedHashMap<>();
    private final Map<String, String> dogsMenu = new LinkedHashMap<>();
    private final Map<String, String> catsInfoMenu = new LinkedHashMap<>();
    private final Map<String, String> dogsInfoMenu = new LinkedHashMap<>();
    private final Map<String, String> catsTakeMenu = new LinkedHashMap<>();
    private final Map<String, String> dogsTakeMenu = new LinkedHashMap<>();
    private final Map<CallbackData, String> fileMapper = new EnumMap<>(CallbackData.class);

    private final Map<String, String> dogsChoseMenu = new LinkedHashMap<>();
    private final Map<String, String> catsChoseMenu = new LinkedHashMap<>();

    private final Map<String, String> mainMenu = new LinkedHashMap<>();

    private final Map<String, String> startVolunteer = new LinkedHashMap<>();
    private final Map<String, String> volunteerMenu = new LinkedHashMap<>();


    /*
     * Нестатический блок инициализации кнопок и методов
     */ {

        mainMenu.put(CallbackData.CATS.getTitle(), "\uD83D\uDC08 Приют для кошек");
        mainMenu.put(CallbackData.DOGS.getTitle(), "\uD83D\uDC15 Приют для собак");

        queryExecutors.put(CallbackData.CATS, this::handleCats);
        queryExecutors.put(CallbackData.DOGS, this::handleDogs);
        queryExecutors.put(CallbackData.CATS_INFO, this::handleCatsInfo);
        queryExecutors.put(CallbackData.DOGS_INFO, this::handleDogsInfo);
        queryExecutors.put(CallbackData.CATS_TAKE, this::handleCatsTake);
        queryExecutors.put(CallbackData.DOGS_TAKE, this::handleDogsTake);

        queryExecutors.put(CallbackData.CATS_SHELTER_CHOSE, this::handleCatsShelterChose);
        queryExecutors.put(CallbackData.DOGS_SHELTER_CHOSE, this::handleDogsShelterChose);
        queryExecutors.put(CallbackData.RESET_SHELTER, this::handleResetShelterChose);

        queryExecutors.put(CallbackData.CATS_SHELTER_INFO, this::handleCatsShelterInfo);
        queryExecutors.put(CallbackData.CATS_SHELTER_WORK_HOURS, this::handleCatsShelterWorkHours);
        queryExecutors.put(CallbackData.CATS_SHELTER_ADDRESS, this::handleCatsShelterAddress);
        queryExecutors.put(CallbackData.CATS_SHELTER_HOW_TO_GET, this::handleCatsShelterHowToGet);
        queryExecutors.put(CallbackData.CATS_SHELTER_ENTRY_PASS, this::handleCatsShelterEntryPass);
        queryExecutors.put(CallbackData.CATS_SHELTER_SAFETY_RULES, this::handleCatsShelterSafetyRules);

        queryExecutors.put(CallbackData.DOGS_SHELTER_INFO, this::handleDogsShelterInfo);
        queryExecutors.put(CallbackData.DOGS_SHELTER_WORK_HOURS, this::handleDogsShelterWorkHours);
        queryExecutors.put(CallbackData.DOGS_SHELTER_ADDRESS, this::handleDogsShelterAddress);
        queryExecutors.put(CallbackData.DOGS_SHELTER_HOW_TO_GET, this::handleDogsShelterHowToGet);
        queryExecutors.put(CallbackData.DOGS_SHELTER_ENTRY_PASS, this::handleDogsShelterEntryPass);
        queryExecutors.put(CallbackData.DOGS_SHELTER_SAFETY_RULES, this::handleDogsShelterSafetyRules);

        queryExecutors.put(CallbackData.CATS_ADOPTION_SAY_HI_RULES, this::handleCatsAdoptionSayHiRules);
        queryExecutors.put(CallbackData.CATS_ADOPTION_DOCUMENTS, this::handleCatsAdoptionDocuments);
        queryExecutors.put(CallbackData.CATS_ADOPTION_TRANSPORTATION_RULES, this::handleCatsAdoptionTransportationRules);
        queryExecutors.put(CallbackData.CATS_ADOPTION_CHILD_HOUSE_RULES, this::handleCatsAdoptionChildHouseRules);
        queryExecutors.put(CallbackData.CATS_ADOPTION_ADULT_HOUSE_RULES, this::handleCatsAdoptionAdultHouseRules);
        queryExecutors.put(CallbackData.CATS_ADOPTION_DISABLED_HOUSE_RULES, this::handleCatsAdoptionDisabledHouseRules);
        queryExecutors.put(CallbackData.CATS_ADOPTION_REASONS_FOR_REFUSAL, this::handleCatsAdoptionReasonsForRefusal);

        queryExecutors.put(CallbackData.DOGS_ADOPTION_SAY_HI_RULES, this::handleDogsAdoptionSayHiRules);
        queryExecutors.put(CallbackData.DOGS_ADOPTION_DOCUMENTS, this::handleDogsAdoptionDocuments);
        queryExecutors.put(CallbackData.DOGS_ADOPTION_TRANSPORTATION_RULES, this::handleDogsAdoptionTransportationRules);
        queryExecutors.put(CallbackData.DOGS_ADOPTION_CHILD_HOUSE_RULES, this::handleDogsAdoptionChildHouseRules);
        queryExecutors.put(CallbackData.DOGS_ADOPTION_ADULT_HOUSE_RULES, this::handleDogsAdoptionAdultHouseRules);
        queryExecutors.put(CallbackData.DOGS_ADOPTION_DISABLED_HOUSE_RULES, this::handleDogsAdoptionDisabledHouseRules);
        queryExecutors.put(CallbackData.DOGS_ADOPTION_DOG_HANDLER_RULES, this::handleDogsAdoptionDogHandlerRules);
        queryExecutors.put(CallbackData.DOGS_ADOPTION_DOG_HANDLERS_LIST, this::handleDogsAdoptionDogHandlersList);
        queryExecutors.put(CallbackData.DOGS_ADOPTION_REASONS_FOR_REFUSAL, this::handleDogsAdoptionReasonsForRefusal);

        queryExecutors.put(CallbackData.REPORT, this::handleReport);
        queryExecutors.put(CallbackData.CONTACTS, this::handleContacts);
        queryExecutors.put(CallbackData.HELP, this::handleVolunteerHelp);

        queryExecutors.put(CallbackData.START_VOLUNTEER, this::handleStartVolunteer);
        queryExecutors.put(CallbackData.ADD_ADOPTER, this::handleAddAdopter);
        queryExecutors.put(CallbackData.CHECK_REPORTS, this::handleCheckReports);
        queryExecutors.put(CallbackData.EXTEND_TRIAL, this::handleExtendTrial);
        queryExecutors.put(CallbackData.KEEP_ANIMAL, this::handleKeepAnimal);

        startVolunteer.put(CallbackData.START_VOLUNTEER.getTitle(), CallbackData.START_VOLUNTEER.getDescription());
        volunteerMenu.put(CallbackData.ADD_ADOPTER.getTitle(), CallbackData.ADD_ADOPTER.getDescription());
        volunteerMenu.put(CallbackData.CHECK_REPORTS.getTitle(), CallbackData.CHECK_REPORTS.getDescription());
        volunteerMenu.put(CallbackData.EXTEND_TRIAL.getTitle(), CallbackData.EXTEND_TRIAL.getDescription());
        volunteerMenu.put(CallbackData.KEEP_ANIMAL.getTitle(), CallbackData.KEEP_ANIMAL.getDescription());

        catsMenu.put(CallbackData.CATS_INFO.getTitle(), CallbackData.CATS_INFO.getDescription());
        catsMenu.put(CallbackData.CATS_TAKE.getTitle(), CallbackData.CATS_TAKE.getDescription());
        catsMenu.put(CallbackData.REPORT.getTitle(), CallbackData.REPORT.getDescription());
        catsMenu.put(CallbackData.HELP.getTitle(), CallbackData.HELP.getDescription());
        catsMenu.put(CallbackData.RESET_SHELTER.getTitle(), CallbackData.RESET_SHELTER.getDescription());

        dogsMenu.put(CallbackData.DOGS_INFO.getTitle(), CallbackData.DOGS_INFO.getDescription());
        dogsMenu.put(CallbackData.DOGS_TAKE.getTitle(), CallbackData.DOGS_TAKE.getDescription());
        dogsMenu.put(CallbackData.REPORT.getTitle(), CallbackData.REPORT.getDescription());
        dogsMenu.put(CallbackData.HELP.getTitle(), CallbackData.HELP.getDescription());
        dogsMenu.put(CallbackData.RESET_SHELTER.getTitle(), CallbackData.RESET_SHELTER.getDescription());

        catsInfoMenu.put(CallbackData.CATS_SHELTER_INFO.getTitle(), CallbackData.CATS_SHELTER_INFO.getDescription());
        catsInfoMenu.put(CallbackData.CATS_SHELTER_WORK_HOURS.getTitle(), CallbackData.CATS_SHELTER_WORK_HOURS.getDescription());
        catsInfoMenu.put(CallbackData.CATS_SHELTER_ADDRESS.getTitle(), CallbackData.CATS_SHELTER_ADDRESS.getDescription());
        catsInfoMenu.put(CallbackData.CATS_SHELTER_HOW_TO_GET.getTitle(), CallbackData.CATS_SHELTER_HOW_TO_GET.getDescription());
        catsInfoMenu.put(CallbackData.CATS_SHELTER_ENTRY_PASS.getTitle(), CallbackData.CATS_SHELTER_ENTRY_PASS.getDescription());
        catsInfoMenu.put(CallbackData.CATS_SHELTER_SAFETY_RULES.getTitle(), CallbackData.CATS_SHELTER_SAFETY_RULES.getDescription());
        catsInfoMenu.put(CallbackData.CONTACTS.getTitle(), CallbackData.CONTACTS.getDescription());
        catsInfoMenu.put(CallbackData.HELP.getTitle(), CallbackData.HELP.getDescription());

        dogsInfoMenu.put(CallbackData.DOGS_SHELTER_INFO.getTitle(), CallbackData.DOGS_SHELTER_INFO.getDescription());
        dogsInfoMenu.put(CallbackData.DOGS_SHELTER_WORK_HOURS.getTitle(), CallbackData.DOGS_SHELTER_WORK_HOURS.getDescription());
        dogsInfoMenu.put(CallbackData.DOGS_SHELTER_ADDRESS.getTitle(), CallbackData.DOGS_SHELTER_ADDRESS.getDescription());
        dogsInfoMenu.put(CallbackData.DOGS_SHELTER_HOW_TO_GET.getTitle(), CallbackData.DOGS_SHELTER_HOW_TO_GET.getDescription());
        dogsInfoMenu.put(CallbackData.DOGS_SHELTER_ENTRY_PASS.getTitle(), CallbackData.DOGS_SHELTER_ENTRY_PASS.getDescription());
        dogsInfoMenu.put(CallbackData.DOGS_SHELTER_SAFETY_RULES.getTitle(), CallbackData.DOGS_SHELTER_SAFETY_RULES.getDescription());
        dogsInfoMenu.put(CallbackData.CONTACTS.getTitle(), CallbackData.CONTACTS.getDescription());
        dogsInfoMenu.put(CallbackData.HELP.getTitle(), CallbackData.HELP.getDescription());

        catsTakeMenu.put(CallbackData.CATS_ADOPTION_SAY_HI_RULES.getTitle(), CallbackData.CATS_ADOPTION_SAY_HI_RULES.getDescription());
        catsTakeMenu.put(CallbackData.CATS_ADOPTION_DOCUMENTS.getTitle(), CallbackData.CATS_ADOPTION_DOCUMENTS.getDescription());
        catsTakeMenu.put(CallbackData.CATS_ADOPTION_TRANSPORTATION_RULES.getTitle(), CallbackData.CATS_ADOPTION_TRANSPORTATION_RULES.getDescription());
        catsTakeMenu.put(CallbackData.CATS_ADOPTION_CHILD_HOUSE_RULES.getTitle(), CallbackData.CATS_ADOPTION_CHILD_HOUSE_RULES.getDescription());
        catsTakeMenu.put(CallbackData.CATS_ADOPTION_ADULT_HOUSE_RULES.getTitle(), CallbackData.CATS_ADOPTION_ADULT_HOUSE_RULES.getDescription());
        catsTakeMenu.put(CallbackData.CATS_ADOPTION_DISABLED_HOUSE_RULES.getTitle(), CallbackData.CATS_ADOPTION_DISABLED_HOUSE_RULES.getDescription());
        catsTakeMenu.put(CallbackData.CATS_ADOPTION_REASONS_FOR_REFUSAL.getTitle(), CallbackData.CATS_ADOPTION_REASONS_FOR_REFUSAL.getDescription());
        catsTakeMenu.put(CallbackData.CONTACTS.getTitle(), CallbackData.CONTACTS.getDescription());
        catsTakeMenu.put(CallbackData.HELP.getTitle(), CallbackData.HELP.getDescription());

        dogsTakeMenu.put(CallbackData.DOGS_ADOPTION_SAY_HI_RULES.getTitle(), CallbackData.DOGS_ADOPTION_SAY_HI_RULES.getDescription());
        dogsTakeMenu.put(CallbackData.DOGS_ADOPTION_DOCUMENTS.getTitle(), CallbackData.DOGS_ADOPTION_DOCUMENTS.getDescription());
        dogsTakeMenu.put(CallbackData.DOGS_ADOPTION_TRANSPORTATION_RULES.getTitle(), CallbackData.DOGS_ADOPTION_TRANSPORTATION_RULES.getDescription());
        dogsTakeMenu.put(CallbackData.DOGS_ADOPTION_CHILD_HOUSE_RULES.getTitle(), CallbackData.DOGS_ADOPTION_CHILD_HOUSE_RULES.getDescription());
        dogsTakeMenu.put(CallbackData.DOGS_ADOPTION_ADULT_HOUSE_RULES.getTitle(), CallbackData.DOGS_ADOPTION_ADULT_HOUSE_RULES.getDescription());
        dogsTakeMenu.put(CallbackData.DOGS_ADOPTION_DISABLED_HOUSE_RULES.getTitle(), CallbackData.DOGS_ADOPTION_DISABLED_HOUSE_RULES.getDescription());
        dogsTakeMenu.put(CallbackData.DOGS_ADOPTION_REASONS_FOR_REFUSAL.getTitle(), CallbackData.DOGS_ADOPTION_REASONS_FOR_REFUSAL.getDescription());
        dogsTakeMenu.put(CallbackData.DOGS_ADOPTION_DOG_HANDLER_RULES.getTitle(), CallbackData.DOGS_ADOPTION_DOG_HANDLER_RULES.getDescription());
        dogsTakeMenu.put(CallbackData.DOGS_ADOPTION_DOG_HANDLERS_LIST.getTitle(), CallbackData.DOGS_ADOPTION_DOG_HANDLERS_LIST.getDescription());
        dogsTakeMenu.put(CallbackData.CONTACTS.getTitle(), CallbackData.CONTACTS.getDescription());
        dogsTakeMenu.put(CallbackData.HELP.getTitle(), CallbackData.HELP.getDescription());

        fileMapper.put(CallbackData.CATS_SHELTER_INFO, "https://lukaselektro.ru/wp-content/uploads/2023/09/cats/Cats_Shelter_Info.pdf");
        fileMapper.put(CallbackData.CATS_SHELTER_ENTRY_PASS, "https://lukaselektro.ru/wp-content/uploads/2023/09/cats/Cats_Shelter_Entry_Pass.pdf");
        fileMapper.put(CallbackData.CATS_SHELTER_SAFETY_RULES, "https://lukaselektro.ru/wp-content/uploads/2023/09/cats/Cats_Shelter_Safety_Rules.pdf");
        fileMapper.put(CallbackData.CATS_SHELTER_HOW_TO_GET, "https://i.pinimg.com/originals/db/b3/bb/dbb3bb09a404fa8151a6eb3e30f5963a.jpg");
        fileMapper.put(CallbackData.DOGS_SHELTER_INFO, "https://lukaselektro.ru/wp-content/uploads/2023/09/dogs/Dogs_Shelter_Info.pdf");
        fileMapper.put(CallbackData.DOGS_SHELTER_ENTRY_PASS, "https://lukaselektro.ru/wp-content/uploads/2023/09/dogs/Dogs_Shelter_Entry_Pass.pdf");
        fileMapper.put(CallbackData.DOGS_SHELTER_SAFETY_RULES, "https://lukaselektro.ru/wp-content/uploads/2023/09/dogs/Dogs_Shelter_Safety_Rules.pdf");
        fileMapper.put(CallbackData.DOGS_SHELTER_HOW_TO_GET, "http://ladystyle.su/articles/upload/image/map_large.jpg");

        fileMapper.put(CallbackData.CATS_ADOPTION_SAY_HI_RULES, "https://lukaselektro.ru/wp-content/uploads/2023/09/cats/Cats_Shelter_Say_Hi_Rules.pdf");
        fileMapper.put(CallbackData.CATS_ADOPTION_DOCUMENTS, "https://lukaselektro.ru/wp-content/uploads/2023/09/cats/Cats_Shelter_Documents.pdf");
        fileMapper.put(CallbackData.CATS_ADOPTION_TRANSPORTATION_RULES, "https://lukaselektro.ru/wp-content/uploads/2023/09/cats/Cats_Shelter_Transportation_Rules.pdf");
        fileMapper.put(CallbackData.CATS_ADOPTION_CHILD_HOUSE_RULES, "https://lukaselektro.ru/wp-content/uploads/2023/09/cats/Cats_Shelter_Child_House_Rules.pdf");
        fileMapper.put(CallbackData.CATS_ADOPTION_ADULT_HOUSE_RULES, "https://lukaselektro.ru/wp-content/uploads/2023/09/cats/Cats_Shelter_Adult_House_Rules.pdf");
        fileMapper.put(CallbackData.CATS_ADOPTION_DISABLED_HOUSE_RULES, "https://lukaselektro.ru/wp-content/uploads/2023/09/cats/Cats_Shelter_Disabled_House_Rules.pdf");
        fileMapper.put(CallbackData.CATS_ADOPTION_REASONS_FOR_REFUSAL, "https://lukaselektro.ru/wp-content/uploads/2023/09/cats/Cats_Shelter_Reasons_for_Refusal.pdf");

        fileMapper.put(CallbackData.DOGS_ADOPTION_SAY_HI_RULES, "https://lukaselektro.ru/wp-content/uploads/2023/09/dogs/Dogs_Shelter_Say_Hi_Rules.pdf");
        fileMapper.put(CallbackData.DOGS_ADOPTION_DOCUMENTS, "https://lukaselektro.ru/wp-content/uploads/2023/09/dogs/Dogs_Shelter_Documents.pdf");
        fileMapper.put(CallbackData.DOGS_ADOPTION_TRANSPORTATION_RULES, "https://lukaselektro.ru/wp-content/uploads/2023/09/dogs/Dogs_Shelter_Transportation_Rules.pdf");
        fileMapper.put(CallbackData.DOGS_ADOPTION_CHILD_HOUSE_RULES, "https://lukaselektro.ru/wp-content/uploads/2023/09/dogs/Dogs_Shelter_Child_House_Rules.pdf");
        fileMapper.put(CallbackData.DOGS_ADOPTION_ADULT_HOUSE_RULES, "https://lukaselektro.ru/wp-content/uploads/2023/09/dogs/Dogs_Shelter_Adult_House_Rules.pdf");
        fileMapper.put(CallbackData.DOGS_ADOPTION_DISABLED_HOUSE_RULES, "https://lukaselektro.ru/wp-content/uploads/2023/09/dogs/Dogs_Shelter_Disabled_House_Rules.pdf");
        fileMapper.put(CallbackData.DOGS_ADOPTION_DOG_HANDLER_RULES, "https://lukaselektro.ru/wp-content/uploads/2023/09/dogs/Dogs_Shelter_Dog_Handler_Rules.pdf");
        fileMapper.put(CallbackData.DOGS_ADOPTION_DOG_HANDLERS_LIST, "https://lukaselektro.ru/wp-content/uploads/2023/09/dogs/Dogs_Shelter_Dog_Handlers_List.pdf");
        fileMapper.put(CallbackData.DOGS_ADOPTION_REASONS_FOR_REFUSAL, "https://lukaselektro.ru/wp-content/uploads/2023/09/dogs/Dogs_Shelter_Reasons_for_Refusal.pdf");
    }

    public CallbackQueryHandler(final TelegramBotService telegramBotService,
                                final ShelterService shelterService,
                                final UserService userService,
                                final UserReportService userReportService,
                                final UserReportPhotoService userReportPhotoService,
                                final PetService petService, final MarkupHelper markupHelper, CommandHandler commandHandler) {
        this.telegramBotService = telegramBotService;
        this.shelterService = shelterService;
        this.userService = userService;
        this.userReportService = userReportService;
        this.userReportPhotoService = userReportPhotoService;
        this.petService = petService;
        this.markupHelper = markupHelper;
        this.commandHandler = commandHandler;
        fillSheltersChooseMenu();
        log.info("Constructor CallbackQueryHandler");
    }

    public void handle(User user, Chat chat, String data) {
        try {
            CallbackData[] queries = CallbackData.values();
            for (CallbackData query : queries) {
                if (Objects.equals(query.getTitle(), data)) {
                    queryExecutors.get(query).accept(user, chat);
                    return;
                }
            }
            for (String catsHashCode : catsChoseMenu.keySet()) {
                if (Objects.equals(catsHashCode, data)) {
                    userService.updateUserSelectedShelterId(chat.id(), data);
                    queryExecutors.get(CallbackData.CATS_SHELTER_CHOSE).accept(user, chat);
                    return;
                }
            }
            for (String dogsHashCode : dogsChoseMenu.keySet()) {
                if (Objects.equals(dogsHashCode, data)) {
                    userService.updateUserSelectedShelterId(chat.id(), data);
                    queryExecutors.get(CallbackData.DOGS_SHELTER_CHOSE).accept(user, chat);
                    return;
                }
            }
            log.info("Handle CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error Handle CallbackQueryHandler");
        }
    }

    private void fillSheltersChooseMenu() {
        for (Shelter shelter : shelterService.getShelterByType(PetType.CAT)) {
            catsChoseMenu.put(String.valueOf(shelter.getId()), shelter.getName());
        }
        for (Shelter shelter : shelterService.getShelterByType(PetType.DOG)) {
            dogsChoseMenu.put(String.valueOf(shelter.getId()), shelter.getName());
        }
    }

    /**
     * Метод отвечающий за переход в меню связанных с собаками
     *
     * @param user User
     * @param chat Chat
     */
    private void handleDogs(User user, Chat chat) {
        try {
            String text = CallbackData.DOGS_SHELTER_CHOSE.getDescription();
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(dogsChoseMenu), null);
            log.info("HandleDogs CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleDogs CallbackQueryHandler");
        }
    }

    private void handleDogsShelterChose(User user, Chat chat) {
        try {
            String text = CallbackData.DOGS.getDescription();
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(dogsMenu), ParseMode.Markdown);
            log.info("HandleDogsShelterChose CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleDogsShelterChose CallbackQueryHandler");
        }
    }

    /**
     * Метод отвечающий за переход в меню связанных с кошками
     *
     * @param user User
     * @param chat Chat
     */
    private void handleCats(User user, Chat chat) {
        try {
            String text = CallbackData.CATS_SHELTER_CHOSE.getDescription();
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(catsChoseMenu), null);
            log.info("HandleCats CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleCats CallbackQueryHandler");
        }
    }

    private void handleCatsShelterChose(User user, Chat chat) {
        try {
            String text = CallbackData.CATS.getDescription();
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(catsMenu), ParseMode.Markdown);
            log.info("HandleCatsShelterChose CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleCatsShelterChose CallbackQueryHandler");
        }
    }

    private void handleCatsInfo(User user, Chat chat) {
        try {
            String name = user.firstName();
            String text = name + ", какую *информацию о приюте для кошек* вы желаете получить? Кликните по нужной кнопке меню:";
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(catsInfoMenu), ParseMode.Markdown);
            log.info("HandleCatsInfo CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleCatsInfo CallbackQueryHandler");
        }
    }

    private void handleDogsInfo(User user, Chat chat) {
        try {
            String name = user.firstName();
            String text = name + ", какую *информацию о приюте для собак* вы желаете получить? Кликните по нужной кнопке меню:";
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(dogsInfoMenu), ParseMode.Markdown);
            log.info("HandleDogsInfo CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleDogsInfo CallbackQueryHandler");
        }
    }

    private void handleCatsTake(User user, Chat chat) {
        try {
            String name = user.firstName();
            String text = name + ", желаете *взять себе кошку* из нашего приюта? Мы поможем вам разобраться с *бюрократическими и бытовыми* вопросами. Выберите нужный пункт меню:";
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(catsTakeMenu), ParseMode.Markdown);
            log.info("HandleCatsTake CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleCatsTake CallbackQueryHandler");
        }
    }

    private void handleDogsTake(User user, Chat chat) {
        try {
            String name = user.firstName();
            String text = name + ", желаете *взять себе собаку* из нашего приюта? Мы поможем вам разобраться с *бюрократическими и бытовыми* вопросами. Выберите нужный пункт меню:";
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(dogsTakeMenu), ParseMode.Markdown);
            log.info("HandleDogsTake CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleDogsTake CallbackQueryHandler");
        }
    }


    private void handleCatsShelterInfo(User user, Chat chat) {
        String caption = CallbackData.CATS_SHELTER_INFO.getDescription();
        String file = fileMapper.get(CallbackData.CATS_SHELTER_INFO);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleCatsShelterWorkHours(User user, Chat chat) {
        Shelter catShelter = shelterService.getShelterById(userService.getUserByChatId(chat.id()).getSelectedShelterId());
        String workSchedule = "⌛ Часы работы приюта для кошек: *" + catShelter.getWorkSchedule() + "*";
        telegramBotService.sendMessage(chat.id(), workSchedule, null, ParseMode.Markdown);
    }

    private void handleCatsShelterAddress(User user, Chat chat) {
        Shelter catShelter = shelterService.getShelterById(userService.getUserByChatId(chat.id()).getSelectedShelterId());
        String address = "\uD83D\uDCCD Адрес приюта для кошек: *" + catShelter.getAddress() + "*";
        telegramBotService.sendMessage(chat.id(), address, null, ParseMode.Markdown);
        telegramBotService.sendLocation(chat.id(), catShelter.getLatitude(), catShelter.getLongitude());
    }

    private void handleCatsShelterSafetyRules(User user, Chat chat) {
        String caption = CallbackData.CATS_SHELTER_SAFETY_RULES.getDescription();
        String file = fileMapper.get(CallbackData.CATS_SHELTER_SAFETY_RULES);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleCatsShelterEntryPass(User user, Chat chat) {
        String caption = CallbackData.CATS_SHELTER_ENTRY_PASS.getDescription();
        String file = fileMapper.get(CallbackData.CATS_SHELTER_ENTRY_PASS);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleCatsShelterHowToGet(User user, Chat chat) {
        String caption = CallbackData.CATS_SHELTER_HOW_TO_GET.getDescription();
        String file = fileMapper.get(CallbackData.CATS_SHELTER_HOW_TO_GET);
        telegramBotService.sendPicture(chat.id(), file, caption);
    }

    private void handleDogsShelterInfo(User user, Chat chat) {
        String caption = CallbackData.DOGS_SHELTER_INFO.getDescription();
        String file = fileMapper.get(CallbackData.DOGS_SHELTER_INFO);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleDogsShelterWorkHours(User user, Chat chat) {
        Shelter dogShelter = shelterService.getShelterById(userService.getUserByChatId(chat.id()).getSelectedShelterId());
        String workSchedule = "⌛ Часы работы приюта для собак: *" + dogShelter.getWorkSchedule() + "*";
        telegramBotService.sendMessage(chat.id(), workSchedule, null, ParseMode.Markdown);
    }

    private void handleDogsShelterAddress(User user, Chat chat) {
        Shelter dogShelter = shelterService.getShelterById(userService.getUserByChatId(chat.id()).getSelectedShelterId());
        String address = "\uD83D\uDCCD Адрес приюта для собак: *" + dogShelter.getAddress() + "*";
        telegramBotService.sendMessage(chat.id(), address, null, ParseMode.Markdown);
        telegramBotService.sendLocation(chat.id(), dogShelter.getLatitude(), dogShelter.getLongitude());
    }

    private void handleDogsShelterSafetyRules(User user, Chat chat) {
        String caption = CallbackData.DOGS_SHELTER_SAFETY_RULES.getDescription();
        String file = fileMapper.get(CallbackData.DOGS_SHELTER_SAFETY_RULES);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleDogsShelterEntryPass(User user, Chat chat) {
        String caption = CallbackData.DOGS_SHELTER_ENTRY_PASS.getDescription();
        String file = fileMapper.get(CallbackData.DOGS_SHELTER_ENTRY_PASS);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleDogsShelterHowToGet(User user, Chat chat) {
        String caption = CallbackData.DOGS_SHELTER_HOW_TO_GET.getDescription();
        String file = fileMapper.get(CallbackData.DOGS_SHELTER_HOW_TO_GET);
        telegramBotService.sendPicture(chat.id(), file, caption);
    }

    private void handleCatsAdoptionSayHiRules(User user, Chat chat) {
        String caption = CallbackData.CATS_ADOPTION_SAY_HI_RULES.getDescription();
        String file = fileMapper.get(CallbackData.CATS_ADOPTION_SAY_HI_RULES);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleCatsAdoptionDocuments(User user, Chat chat) {
        String caption = CallbackData.CATS_ADOPTION_DOCUMENTS.getDescription();
        String file = fileMapper.get(CallbackData.CATS_ADOPTION_DOCUMENTS);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleCatsAdoptionTransportationRules(User user, Chat chat) {
        String caption = CallbackData.CATS_ADOPTION_TRANSPORTATION_RULES.getDescription();
        String file = fileMapper.get(CallbackData.CATS_ADOPTION_TRANSPORTATION_RULES);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleCatsAdoptionChildHouseRules(User user, Chat chat) {
        String caption = CallbackData.CATS_ADOPTION_CHILD_HOUSE_RULES.getDescription();
        String file = fileMapper.get(CallbackData.CATS_ADOPTION_CHILD_HOUSE_RULES);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleCatsAdoptionAdultHouseRules(User user, Chat chat) {
        String caption = CallbackData.CATS_ADOPTION_ADULT_HOUSE_RULES.getDescription();
        String file = fileMapper.get(CallbackData.CATS_ADOPTION_ADULT_HOUSE_RULES);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleCatsAdoptionDisabledHouseRules(User user, Chat chat) {
        String caption = CallbackData.CATS_ADOPTION_DISABLED_HOUSE_RULES.getDescription();
        String file = fileMapper.get(CallbackData.CATS_ADOPTION_DISABLED_HOUSE_RULES);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleCatsAdoptionReasonsForRefusal(User user, Chat chat) {
        String caption = CallbackData.CATS_ADOPTION_REASONS_FOR_REFUSAL.getDescription();
        String file = fileMapper.get(CallbackData.CATS_ADOPTION_REASONS_FOR_REFUSAL);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleDogsAdoptionSayHiRules(User user, Chat chat) {
        String caption = CallbackData.DOGS_ADOPTION_SAY_HI_RULES.getDescription();
        String file = fileMapper.get(CallbackData.DOGS_ADOPTION_SAY_HI_RULES);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleDogsAdoptionDocuments(User user, Chat chat) {
        String caption = CallbackData.DOGS_ADOPTION_DOCUMENTS.getDescription();
        String file = fileMapper.get(CallbackData.DOGS_ADOPTION_DOCUMENTS);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleDogsAdoptionTransportationRules(User user, Chat chat) {
        String caption = CallbackData.DOGS_ADOPTION_TRANSPORTATION_RULES.getDescription();
        String file = fileMapper.get(CallbackData.DOGS_ADOPTION_TRANSPORTATION_RULES);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleDogsAdoptionChildHouseRules(User user, Chat chat) {
        String caption = CallbackData.DOGS_ADOPTION_CHILD_HOUSE_RULES.getDescription();
        String file = fileMapper.get(CallbackData.DOGS_ADOPTION_CHILD_HOUSE_RULES);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleDogsAdoptionAdultHouseRules(User user, Chat chat) {
        String caption = CallbackData.DOGS_ADOPTION_ADULT_HOUSE_RULES.getDescription();
        String file = fileMapper.get(CallbackData.DOGS_ADOPTION_ADULT_HOUSE_RULES);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleDogsAdoptionDisabledHouseRules(User user, Chat chat) {
        String caption = CallbackData.DOGS_ADOPTION_DISABLED_HOUSE_RULES.getDescription();
        String file = fileMapper.get(CallbackData.DOGS_ADOPTION_DISABLED_HOUSE_RULES);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleDogsAdoptionDogHandlerRules(User user, Chat chat) {
        String caption = CallbackData.DOGS_ADOPTION_DOG_HANDLER_RULES.getDescription();
        String file = fileMapper.get(CallbackData.DOGS_ADOPTION_DOG_HANDLER_RULES);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleDogsAdoptionDogHandlersList(User user, Chat chat) {
        String caption = CallbackData.DOGS_ADOPTION_DOG_HANDLERS_LIST.getDescription();
        String file = fileMapper.get(CallbackData.DOGS_ADOPTION_DOG_HANDLERS_LIST);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleDogsAdoptionReasonsForRefusal(User user, Chat chat) {
        String caption = CallbackData.DOGS_ADOPTION_REASONS_FOR_REFUSAL.getDescription();
        String file = fileMapper.get(CallbackData.DOGS_ADOPTION_REASONS_FOR_REFUSAL);
        telegramBotService.sendPDFDocument(chat.id(), file, caption);
    }

    private void handleReport(User user, Chat chat) {
        try {
            Long userId = user.id();

            com.example.petshelter.entity.User thisUser = userService.getUserByChatId(userId);
            thisUser.setActiveMenu(Menu.REPORT);
            userService.updateUser(thisUser.getId(),thisUser);

            if (!thisUser.getRole().equals(ADOPTER)) {

                String txt = user.firstName() + ", прислать отчет о питомце может только усыновитель, которым вы, к сожалению, еще не стали.";
                telegramBotService.sendMessage(chat.id(), txt, null, ParseMode.Markdown);

            } else {

                UserReport thisReport = userReportService.getUserReportByUserIdAndStatusCreated(userId);

                if (thisReport == null) {


                    UserReport userReport = new UserReport(0L, null, null, null, LocalDateTime.now().toLocalDate(), UserReportStatus.CREATED, thisUser, null);
                    userReportService.addUserReport(userReport);

                    String text =
                            """
                                    В отчет о животном должна обязательно входить следующая информация:

                                    - *Фото животного.*
                                    - *Рацион животного.*
                                    - *Общее самочувствие и привыкание к новому месту.*
                                    - *Изменение в поведении: отказ от старых привычек, приобретение новых.*
                                    
                                    """
                            + commandHandler.getListOfPetsForAdopter(thisUser.getId());

                    telegramBotService.sendMessage(chat.id(), text, null, ParseMode.Markdown);

                } else {

                    String text = "У вас есть незаполненный отчет. Прошу его заполнить, прежде чем начинать новый.";
                    telegramBotService.sendMessage(chat.id(), text, null, ParseMode.Markdown);

                    UserReportPhoto thisReportPhoto = userReportPhotoService.findUserReportPhoto(thisReport.getId());

                    if (thisReport.getPet() == null) {
                        String txt = commandHandler.getListOfPetsForAdopter(thisUser.getId());
                        telegramBotService.sendMessage(chat.id(), txt, null, ParseMode.Markdown);
                        return;
                    } else if (thisReportPhoto == null) {
                        String txt = "Пришлите, пожалуйста, фото питомца.";
                        telegramBotService.sendMessage(chat.id(), txt, null, ParseMode.Markdown);
                        return;
                    } else if (thisReport.getPetDiet() == null) {
                        String txt = "Пришлите, пожалуйста, в текстовом сообщении описание рациона питомца.";
                        telegramBotService.sendMessage(chat.id(), txt, null, ParseMode.Markdown);
                        return;
                    } else if (thisReport.getHealth() == null) {
                        String txt = "Пришлите, пожалуйста, в текстовом сообщении описание общего самочувствия питомца и особенности привыкания к новому месту.";
                        telegramBotService.sendMessage(chat.id(), txt, null, ParseMode.Markdown);
                        return;
                    } else {
                        String txt = "Пришлите, пожалуйста, в текстовом сообщении особенности поведения питомца: отказ от старых привычек, приобретение новых.";
                        telegramBotService.sendMessage(chat.id(), txt, null, ParseMode.Markdown);
                        return;
                    }

                }
            }

            log.info("HandleReport CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleReport CallbackQueryHandler");
        }
    }

    private void handleContacts(User user, Chat chat) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton("☎ Отправить свой номер телефона").requestContact(true)
        );
        try {
            telegramBotService.sendContact(chat.id(), "*Кликните по кнопке ниже* для отправки своих контактов", replyKeyboardMarkup, ParseMode.Markdown);
            log.info("handleContacts CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error handleContacts CallbackQueryHandler");
        }
    }

    private void handleVolunteerHelp(User user, Chat chat) {
        try {
            String textVolunteer = "Пользователь @" + chat.username() + " запросил помощь по приюту для животных. Свяжись с ним немедленно!";
            List<com.example.petshelter.entity.User> volunteersList = userService.getVolunteers();
            Map<Long, String> volunteersTgNames = new HashMap<>();
            volunteersList.forEach(v -> volunteersTgNames.put(v.getChatId(), v.getTgUsername()));
            if (!volunteersList.isEmpty()) {
                volunteersTgNames.forEach((chatId, tgName) -> {
                    telegramBotService.sendMessage(chatId, tgName + ", привет! " + textVolunteer);
                });
            } else {
                telegramBotService.sendMessage(chat.id(), "Волонтёров не найдено!");
            }
            String text = "Ожидайте ответа волонтера. Он напишет вам в личном сообщении.";
            telegramBotService.sendMessage(chat.id(), text);
            log.info("handleVolunteerHelp CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error handleVolunteerHelp CallbackQueryHandler");
        }
    }

    private void handleResetShelterChose(User user, Chat chat) {
        try {
            String text = "Выберите новый приют:";
            userService.resetSelectedShelterId(chat.id());
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(mainMenu), ParseMode.Markdown);

            com.example.petshelter.entity.User currentUser = userService.getUserByChatId(chat.id());
            currentUser.setActiveMenu(Menu.MAIN_MENU);
            userService.updateUser(currentUser.getId(),currentUser);

            log.info("handleVolunteerHelp CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error handleVolunteerHelp CallbackQueryHandler");
        }
    }

    private void handleStartVolunteer(User user, Chat chat) {
        try {
            String text = "Меню волонтера";
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(volunteerMenu), ParseMode.Markdown);
            log.info("handleStartVolunteer CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error handleStartVolunteer CallbackQueryHandler");
        }
    }

    private void handleAddAdopter(User user, Chat chat) {
        try {
            String text = getListOfPetsAvailableForAdoption();
            telegramBotService.sendMessage(chat.id(), text);
            log.info("handleAddAdopter CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error handleAddAdopter CallbackQueryHandler");
        }
    }

    @NotNull
    private String getListOfPetsAvailableForAdoption() {
        String text;
        List<Pet> petsToAdopt = petService.getPetsWithStatus(PetStatus.AVAILABLE);
        if (petsToAdopt.isEmpty()) {
            text = "Все животные выданы усыновителям.";
        } else {
            StringBuilder builder = new StringBuilder();
            petsToAdopt.forEach(pet -> builder.append(pet.getId())
                    .append(". ")
                    .append(pet.getSpecies())
                    .append(", ")
                    .append(pet.getNickname())
                    .append(", ")
                    .append(pet.getPetType())
                    .append(" => /pet")
                    .append(pet.getId())
                    .append("\n"));
            text = "Выберите животное, кликнув по нужной ссылке:\n" + builder;
        }
        return text;
    }

    private void handleCheckReports(User user, Chat chat) {
        try {
            String text = "Проверить рапорты";
            telegramBotService.sendMessage(chat.id(), text, null, null);
            log.info("handleCheckReports CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error handleCheckReports CallbackQueryHandler");
        }
    }

    private void handleExtendTrial(User user, Chat chat) {
        try {
            String text = getListOfPetsAvailableForExtendTrial();
            telegramBotService.sendMessage(chat.id(), text, null, null);
            log.info("handleExtendTrial CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error handleExtendTrial CallbackQueryHandler");
        }
    }

    private String getListOfPetsAvailableForExtendTrial() {
        String text;
        List<Pet> petsChosen = petService.getPetsWithStatus(PetStatus.CHOSEN);
        if (petsChosen.isEmpty()) {
            text = "Нет животных на испытательном сроке.";
        } else {
            StringBuilder builder = new StringBuilder();
            petsChosen.forEach(pet -> builder.append(pet.getId())
                    .append(". ")
                    .append(pet.getSpecies())
                    .append(", ")
                    .append(pet.getNickname())
                    .append(", ")
                    .append(pet.getPetType())
                    .append(" => /petExtend")
                    .append(pet.getId())
                    .append("\n"));
            text = "Выберите животное, кликнув по нужной ссылке:\n" + builder;
        }
        return text;
    }

    private void handleKeepAnimal(User user, Chat chat) {
        try {
            com.example.petshelter.entity.User userFromDB = userService.getUserByChatId(chat.id());
            telegramBotService.sendMessage(chat.id(), Templates.getCongratulationText(userFromDB), null, ParseMode.Markdown);
            log.info("handleKeepAnimal CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error handleKeepAnimal CallbackQueryHandler");
        }
    }
}
