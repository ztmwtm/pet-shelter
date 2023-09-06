package com.example.petshelter.handler;

import com.example.petshelter.entity.Shelter;
import com.example.petshelter.helper.MarkupHelper;
import com.example.petshelter.service.ShelterService;
import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.util.CallbackData;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ParseMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * Класс отвечающий за обработку команд поступающих из чата
 */

@Slf4j
@Component
public class CallbackQueryHandler {

    private final Map<CallbackData, BiConsumer<User, Chat>> queryExecutors = new HashMap<>();
    private final TelegramBotService telegramBotService;
    private final ShelterService shelterService;
    private final MarkupHelper markupHelper;
    private final Map<String, String> catsMenu = new LinkedHashMap<>();
    private final Map<String, String> dogsMenu = new LinkedHashMap<>();
    private final Map<String, String> catsInfoMenu = new LinkedHashMap<>();
    private final Map<String, String> dogsInfoMenu = new LinkedHashMap<>();
    private final Map<String, String> catsTakeMenu = new LinkedHashMap<>();
    private final Map<String, String> dogsTakeMenu = new LinkedHashMap<>();
    private final EnumMap<CallbackData, String> fileMapper = new EnumMap<>(CallbackData.class);

    /*
     * Нестатический блок инициализации кнопок и методов
     */
    {
        queryExecutors.put(CallbackData.CATS, this::handleCats);
        queryExecutors.put(CallbackData.DOGS, this::handleDogs);
        queryExecutors.put(CallbackData.CATS_INFO, this::handleCatsInfo);
        queryExecutors.put(CallbackData.DOGS_INFO, this::handleDogsInfo);
        queryExecutors.put(CallbackData.CATS_TAKE, this::handleCatsTake);
        queryExecutors.put(CallbackData.DOGS_TAKE, this::handleDogsTake);
        queryExecutors.put(CallbackData.CATS_SHELTER_INFO, this::handleCatsShelterInfo);
        queryExecutors.put(CallbackData.CATS_SHELTER_WORK_HOURS, this::handleCatsShelterWorkHours);
        queryExecutors.put(CallbackData.REPORT, this::handleReport);
        queryExecutors.put(CallbackData.HELP, this::handleVolunteerHelp);

        catsMenu.put(CallbackData.CATS_INFO.getTitle(), CallbackData.CATS_INFO.getDescription());
        catsMenu.put(CallbackData.CATS_TAKE.getTitle(), CallbackData.CATS_TAKE.getDescription());
        catsMenu.put(CallbackData.REPORT.getTitle(), CallbackData.REPORT.getDescription());
        catsMenu.put(CallbackData.HELP.getTitle(), CallbackData.HELP.getDescription());

        dogsMenu.put(CallbackData.DOGS_INFO.getTitle(), CallbackData.DOGS_INFO.getDescription());
        dogsMenu.put(CallbackData.DOGS_TAKE.getTitle(), CallbackData.DOGS_TAKE.getDescription());
        dogsMenu.put(CallbackData.REPORT.getTitle(), CallbackData.REPORT.getDescription());
        dogsMenu.put(CallbackData.HELP.getTitle(), CallbackData.HELP.getDescription());

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
    }

    @Autowired
    public CallbackQueryHandler(final TelegramBotService telegramBotService, final ShelterService shelterService, final MarkupHelper markupHelper) {
        this.telegramBotService = telegramBotService;
        this.shelterService = shelterService;
        this.markupHelper = markupHelper;
        log.info("Constructor CallbackQueryHandler");
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
            log.info("Hendel CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error Hendel CallbackQueryHandler");
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
            String text = CallbackData.DOGS.getDescription();
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(dogsMenu), null);
            log.info("HandelDogs CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandelDogs CallbackQueryHandler");
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
            String text = CallbackData.CATS.getDescription();
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(catsMenu), null);
            log.info("HandelCats CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandelCats CallbackQueryHandler");
        }
    }

    private void handleCatsInfo(User user, Chat chat) {
        try {
            String name = user.firstName();
            String text = name + ", какую *информацию о приюте для кошек* вы желаете получить? Кликните по нужной кнопке меню:";
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(catsInfoMenu), ParseMode.Markdown);
            log.info("HandelCatsInfo CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandelCatsInfo CallbackQueryHandler");
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
        telegramBotService.sendPDFDocument(chat.id(), file, caption, null, null);
    }

    private void handleCatsShelterWorkHours(User user, Chat chat) {
        Shelter catShelter = shelterService.getShelterByName(CallbackData.CATS.getTitle());
        String workSchedule = "Часы работы приюта для кошек: *" + catShelter.getWorkSchedule() + "*";
        telegramBotService.sendMessage(chat.id(), workSchedule, null, ParseMode.Markdown);
    }

    private void handleReport(User user, Chat chat) {
        try {
            String text = """
                    **Этап 3. Ведение питомца**\s

                    *После того как новый усыновитель забрал животное из приюта, он обязан в течение месяца присылать информацию о том, как животное чувствует себя на новом месте. В ежедневный отчет входит следующая информация:*\s

                    - *Фото животного.*
                    - *Рацион животного.*
                    - *Общее самочувствие и привыкание к новому месту.*
                    - *Изменение в поведении: отказ от старых привычек, приобретение новых.*

                    *Отчет нужно присылать каждый день, ограничений в сутках по времени сдачи отчета нет. Каждый день волонтеры отсматривают все присланные отчеты после 21:00. В случае, если усыновитель недолжным образом заполнял отчет, волонтер через бота может дать обратную связь в стандартной форме: «Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. Пожалуйста, подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного».*\s

                    *В базу новых усыновителей пользователь попадает через волонтера, который его туда заносит. Для усыновителей кошачьего приюта база одна, для собачьего приюта — другая.*\s

                    *Задача бота — принимать на вход информацию и в случае, если пользователь не присылает информации, напоминать об этом, а если проходит более 2 дней, то отправлять запрос волонтеру на связь с усыновителем.*\s

                    *Как только период в 30 дней заканчивается, волонтеры принимают решение о том, остается животное у хозяина или нет. Испытательный срок может быть пройден, может быть продлен на срок еще 14 или 30 дней, а может быть не пройден.*\s

                    - Бот может прислать форму ежедневного отчета.""";
            telegramBotService.sendMessage(chat.id(), text, null, ParseMode.Markdown);
            log.info("HendelReport CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HendelReport CallbackQueryHandler");
        }
    }

    private void handleVolunteerHelp(User user, Chat chat) {
        try {
            String text = "Напишите с чем нужна помощь – отправим ваше сообщение волонтёру";
            telegramBotService.sendMessage(chat.id(), text, null, null);
            log.info("handleVolunteerHelp CallbackQueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error handleVolunteerHelp CallbackQueryHandler");
        }
    }
}
