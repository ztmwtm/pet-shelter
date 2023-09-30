package com.example.petshelter.handler;

import com.example.petshelter.entity.Shelter;
import com.example.petshelter.helper.MarkupHelper;
import com.example.petshelter.service.ShelterService;
import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.service.UserService;
import com.example.petshelter.util.CallbackData;
import com.example.petshelter.type.PetType;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.lang.Nullable;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CallbackQueryHandlerTest {


    @Mock
    private TelegramBotService telegramBotServiceMock;
    @Mock
    private ShelterService shelterServiceMock;
    @Mock
    private UserService userServiceMock;
    @Spy
    private MarkupHelper markupHelper;
    @InjectMocks
    CallbackQueryHandler callbackQueryHandler;

    private static User user;
    private static Chat chat;
    private static final com.example.petshelter.entity.User ourUser = new com.example.petshelter.entity.User();
    private static final Shelter shelter = new Shelter();
    private final static Map<String, String> catsMenu = new LinkedHashMap<>();
    private final static Map<String, String> dogsMenu = new LinkedHashMap<>();
    private final static Map<String, String> catsInfoMenu = new LinkedHashMap<>();
    private final static Map<String, String> dogsInfoMenu = new LinkedHashMap<>();
    private final static Map<String, String> catsTakeMenu = new LinkedHashMap<>();
    private final static Map<String, String> dogsTakeMenu = new LinkedHashMap<>();
    private final static EnumMap<CallbackData, String> fileMapper = new EnumMap<>(CallbackData.class);
    private final static Map<String, String> dogsChoseMenu = new LinkedHashMap<>();
    private final static Map<String, String> catsChoseMenu = new LinkedHashMap<>();
    private final static Map<String, String> mainMenu = new LinkedHashMap<>();
    private final static Map<String, String> volunteermenu = new LinkedHashMap<>();

    static {
        mainMenu.put(CallbackData.CATS.getTitle(), "\uD83D\uDC08 Приют для кошек");
        mainMenu.put(CallbackData.DOGS.getTitle(), "\uD83D\uDC15 Приют для собак");

        volunteermenu.put(CallbackData.ADD_ADOPTER.getTitle(), CallbackData.ADD_ADOPTER.getDescription());
        volunteermenu.put(CallbackData.CHECK_REPORTS.getTitle(), CallbackData.CHECK_REPORTS.getDescription());
        volunteermenu.put(CallbackData.EXTEND_TRIAL.getTitle(), CallbackData.EXTEND_TRIAL.getDescription());
        volunteermenu.put(CallbackData.KEEP_ANIMAL.getTitle(), CallbackData.KEEP_ANIMAL.getDescription());

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

    @BeforeAll
    static void setUpBeforeAll() throws NoSuchFieldException, IllegalAccessException {
        chat = new Chat();
        Field idField = Chat.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(chat, 42L);
        user = new User(55L);
        Field userField = User.class.getDeclaredField("first_name");
        userField.setAccessible(true);
        userField.set(user, "UserNameUUUUsuka");

        ourUser.setSelectedShelterId(5555L);
        shelter.setAddress("addddddres");
        shelter.setWorkSchedule("7-27");
        shelter.setLatitude(58);
        shelter.setLongitude(57);
    }

    public static Stream<Arguments> provideParamsForHandleWithTextTest() {
        return Stream.of(
                Arguments.of(CallbackData.CATS_INFO.getTitle(),
                        user.firstName() + ", какую *информацию о приюте для кошек* вы желаете получить? Кликните по нужной кнопке меню:",
                        catsInfoMenu, ParseMode.Markdown),
                Arguments.of(CallbackData.DOGS_INFO.getTitle(),
                        user.firstName() + ", какую *информацию о приюте для собак* вы желаете получить? Кликните по нужной кнопке меню:",
                        dogsInfoMenu, ParseMode.Markdown),
                Arguments.of(CallbackData.CATS_TAKE.getTitle(),
                        user.firstName() + ", желаете *взять себе кошку* из нашего приюта? Мы поможем вам разобраться с *бюрократическими и бытовыми* вопросами. Выберите нужный пункт меню:",
                        catsTakeMenu, ParseMode.Markdown),
                Arguments.of(CallbackData.DOGS_TAKE.getTitle(),
                        user.firstName() + ", желаете *взять себе собаку* из нашего приюта? Мы поможем вам разобраться с *бюрократическими и бытовыми* вопросами. Выберите нужный пункт меню:",
                        dogsTakeMenu, ParseMode.Markdown),
                Arguments.of(CallbackData.START_VOLUNTEER.getTitle(),
                        "Меню волонтера",
                        volunteermenu, ParseMode.Markdown)
        );
    }

    public static Stream<Arguments> provideParamsForHandleWithNullKeyboardAndNotNullParseMode() {
        return Stream.of(
                Arguments.of(CallbackData.REPORT.getTitle(),
                        """
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

                                - Бот может прислать форму ежедневного отчета.""",
                        ParseMode.Markdown)

        );
    }

    public static Stream<Arguments> provideParamsForHandleWithPdfSend() {
        return Stream.of(
                Arguments.of(CallbackData.DOGS_ADOPTION_REASONS_FOR_REFUSAL.getTitle(),
                        fileMapper.get(CallbackData.DOGS_ADOPTION_REASONS_FOR_REFUSAL),
                        CallbackData.DOGS_ADOPTION_REASONS_FOR_REFUSAL.getDescription()),
                Arguments.of(CallbackData.DOGS_ADOPTION_DOG_HANDLERS_LIST.getTitle(),
                        fileMapper.get(CallbackData.DOGS_ADOPTION_DOG_HANDLERS_LIST),
                        CallbackData.DOGS_ADOPTION_DOG_HANDLERS_LIST.getDescription()),
                Arguments.of(CallbackData.DOGS_ADOPTION_DOG_HANDLER_RULES.getTitle(),
                        fileMapper.get(CallbackData.DOGS_ADOPTION_DOG_HANDLER_RULES),
                        CallbackData.DOGS_ADOPTION_DOG_HANDLER_RULES.getDescription()),
                Arguments.of(CallbackData.DOGS_ADOPTION_DISABLED_HOUSE_RULES.getTitle(),
                        fileMapper.get(CallbackData.DOGS_ADOPTION_DISABLED_HOUSE_RULES),
                        CallbackData.DOGS_ADOPTION_DISABLED_HOUSE_RULES.getDescription()),
                Arguments.of(CallbackData.DOGS_ADOPTION_ADULT_HOUSE_RULES.getTitle(),
                        fileMapper.get(CallbackData.DOGS_ADOPTION_ADULT_HOUSE_RULES),
                        CallbackData.DOGS_ADOPTION_ADULT_HOUSE_RULES.getDescription()),
                Arguments.of(CallbackData.DOGS_ADOPTION_CHILD_HOUSE_RULES.getTitle(),
                        fileMapper.get(CallbackData.DOGS_ADOPTION_CHILD_HOUSE_RULES),
                        CallbackData.DOGS_ADOPTION_CHILD_HOUSE_RULES.getDescription()),
                Arguments.of(CallbackData.DOGS_ADOPTION_TRANSPORTATION_RULES.getTitle(),
                        fileMapper.get(CallbackData.DOGS_ADOPTION_TRANSPORTATION_RULES),
                        CallbackData.DOGS_ADOPTION_TRANSPORTATION_RULES.getDescription()),
                Arguments.of(CallbackData.DOGS_ADOPTION_DOCUMENTS.getTitle(),
                        fileMapper.get(CallbackData.DOGS_ADOPTION_DOCUMENTS),
                        CallbackData.DOGS_ADOPTION_DOCUMENTS.getDescription()),
                Arguments.of(CallbackData.DOGS_ADOPTION_SAY_HI_RULES.getTitle(),
                        fileMapper.get(CallbackData.DOGS_ADOPTION_SAY_HI_RULES),
                        CallbackData.DOGS_ADOPTION_SAY_HI_RULES.getDescription()),
                Arguments.of(CallbackData.DOGS_SHELTER_INFO.getTitle(),
                        fileMapper.get(CallbackData.DOGS_SHELTER_INFO),
                        CallbackData.DOGS_SHELTER_INFO.getDescription()),
                Arguments.of(CallbackData.DOGS_SHELTER_ENTRY_PASS.getTitle(),
                        fileMapper.get(CallbackData.DOGS_SHELTER_ENTRY_PASS),
                        CallbackData.DOGS_SHELTER_ENTRY_PASS.getDescription()),
                Arguments.of(CallbackData.DOGS_SHELTER_SAFETY_RULES.getTitle(),
                        fileMapper.get(CallbackData.DOGS_SHELTER_SAFETY_RULES),
                        CallbackData.DOGS_SHELTER_SAFETY_RULES.getDescription()),

                Arguments.of(CallbackData.CATS_SHELTER_INFO.getTitle(),
                        fileMapper.get(CallbackData.CATS_SHELTER_INFO),
                        CallbackData.CATS_SHELTER_INFO.getDescription()),
                Arguments.of(CallbackData.CATS_SHELTER_SAFETY_RULES.getTitle(),
                        fileMapper.get(CallbackData.CATS_SHELTER_SAFETY_RULES),
                        CallbackData.CATS_SHELTER_SAFETY_RULES.getDescription()),
                Arguments.of(CallbackData.CATS_SHELTER_ENTRY_PASS.getTitle(),
                        fileMapper.get(CallbackData.CATS_SHELTER_ENTRY_PASS),
                        CallbackData.CATS_SHELTER_ENTRY_PASS.getDescription()),
                Arguments.of(CallbackData.CATS_ADOPTION_REASONS_FOR_REFUSAL.getTitle(),
                        fileMapper.get(CallbackData.CATS_ADOPTION_REASONS_FOR_REFUSAL),
                        CallbackData.CATS_ADOPTION_REASONS_FOR_REFUSAL.getDescription()),
                Arguments.of(CallbackData.CATS_ADOPTION_DISABLED_HOUSE_RULES.getTitle(),
                        fileMapper.get(CallbackData.CATS_ADOPTION_DISABLED_HOUSE_RULES),
                        CallbackData.CATS_ADOPTION_DISABLED_HOUSE_RULES.getDescription()),
                Arguments.of(CallbackData.CATS_ADOPTION_ADULT_HOUSE_RULES.getTitle(),
                        fileMapper.get(CallbackData.CATS_ADOPTION_ADULT_HOUSE_RULES),
                        CallbackData.CATS_ADOPTION_ADULT_HOUSE_RULES.getDescription()),
                Arguments.of(CallbackData.CATS_ADOPTION_CHILD_HOUSE_RULES.getTitle(),
                        fileMapper.get(CallbackData.CATS_ADOPTION_CHILD_HOUSE_RULES),
                        CallbackData.CATS_ADOPTION_CHILD_HOUSE_RULES.getDescription()),
                Arguments.of(CallbackData.CATS_ADOPTION_TRANSPORTATION_RULES.getTitle(),
                        fileMapper.get(CallbackData.CATS_ADOPTION_TRANSPORTATION_RULES),
                        CallbackData.CATS_ADOPTION_TRANSPORTATION_RULES.getDescription()),
                Arguments.of(CallbackData.CATS_ADOPTION_DOCUMENTS.getTitle(),
                        fileMapper.get(CallbackData.CATS_ADOPTION_DOCUMENTS),
                        CallbackData.CATS_ADOPTION_DOCUMENTS.getDescription()),
                Arguments.of(CallbackData.CATS_ADOPTION_SAY_HI_RULES.getTitle(),
                        fileMapper.get(CallbackData.CATS_ADOPTION_SAY_HI_RULES),
                        CallbackData.CATS_ADOPTION_SAY_HI_RULES.getDescription())

        );
    }

    public static Stream<Arguments> provideParamsForHandleWithTextAndNullParseModeAndNullKeyboard() {
        return Stream.of(
                Arguments.of(CallbackData.KEEP_ANIMAL.getTitle(),
                        "Окончание испытального срока, животное остается у животного"),
                Arguments.of(CallbackData.EXTEND_TRIAL.getTitle(),
                        "Продлить срок напроизвольное количество дней"),
                Arguments.of(CallbackData.CHECK_REPORTS.getTitle(),
                        "Проверить рапорты"),
                Arguments.of(CallbackData.ADD_ADOPTER.getTitle(),
                        "Добавление усыновителя"),
                Arguments.of(CallbackData.HELP.getTitle(),
                        "Ожидайте ответа волонтера. Он напишет вам в личном сообщении.")
        );
    }

    public static Stream<Arguments> provideParamsForHandleWithPictureSend() {
        return Stream.of(
                Arguments.of(CallbackData.DOGS_SHELTER_HOW_TO_GET.getTitle(),
                        fileMapper.get(CallbackData.DOGS_SHELTER_HOW_TO_GET),
                        CallbackData.DOGS_SHELTER_HOW_TO_GET.getDescription()),
                Arguments.of(CallbackData.CATS_SHELTER_HOW_TO_GET.getTitle(),
                        fileMapper.get(CallbackData.CATS_SHELTER_HOW_TO_GET),
                        CallbackData.CATS_SHELTER_HOW_TO_GET.getDescription())
        );
    }

    @BeforeEach
    void setUpBeforeEach() {
        dogsChoseMenu.clear();
        catsChoseMenu.clear();
    }

    @Test
    void handleCatsTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Shelter shelterCatOne = new Shelter();
        shelterCatOne.setName("cat one");
        shelterCatOne.setId(1L);
        Shelter shelterCatTwo = new Shelter();
        shelterCatTwo.setName("cat two");
        shelterCatTwo.setId(2L);
        Mockito.when(shelterServiceMock.getSheltersByType(PetType.CAT))
                .thenReturn(List.of(shelterCatOne, shelterCatTwo));
        Mockito.when(shelterServiceMock.getSheltersByType(PetType.DOG))
                .thenReturn(Collections.emptyList());
        catsChoseMenu.clear();
        catsChoseMenu.put(String.valueOf(shelterCatOne.getId()), shelterCatOne.getName());
        catsChoseMenu.put(String.valueOf(shelterCatTwo.getId()), shelterCatTwo.getName());

        Method fillSheltersChooseMenuMethod = callbackQueryHandler.getClass().getDeclaredMethod("fillSheltersChooseMenu");
        fillSheltersChooseMenuMethod.setAccessible(true);
        fillSheltersChooseMenuMethod.invoke(callbackQueryHandler);

        callbackQueryHandler.handle(user, chat, "cats");
        Mockito.verify(telegramBotServiceMock, Mockito.times(1))
                .sendMessage(chat.id(), CallbackData.CATS_SHELTER_CHOSE.getDescription(),
                        markupHelper.buildMenu(catsChoseMenu), null);
    }

    @Test
    void handleDogsTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Shelter shelterDogOne = new Shelter();
        shelterDogOne.setName("dog one");
        shelterDogOne.setId(1222L);
        Shelter shelterDogTwo = new Shelter();
        shelterDogTwo.setName("dog two");
        shelterDogTwo.setId(2323232L);
        Mockito.when(shelterServiceMock.getSheltersByType(PetType.CAT))
                .thenReturn(Collections.emptyList());
        Mockito.when(shelterServiceMock.getSheltersByType(PetType.DOG))
                .thenReturn(List.of(shelterDogOne, shelterDogTwo));

        dogsChoseMenu.put(String.valueOf(shelterDogOne.getId()), shelterDogOne.getName());
        dogsChoseMenu.put(String.valueOf(shelterDogTwo.getId()), shelterDogTwo.getName());

        Method fillSheltersChooseMenuMethod = callbackQueryHandler.getClass().getDeclaredMethod("fillSheltersChooseMenu");
        fillSheltersChooseMenuMethod.setAccessible(true);
        fillSheltersChooseMenuMethod.invoke(callbackQueryHandler);

        callbackQueryHandler.handle(user, chat, "dogs");
        Mockito.verify(telegramBotServiceMock, Mockito.times(1))
                .sendMessage(chat.id(), CallbackData.DOGS_SHELTER_CHOSE.getDescription(),
                        markupHelper.buildMenu(dogsChoseMenu), null);
    }

    @Test
    void handleCatsShelterChoseTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Shelter shelterCatOne = new Shelter();
        shelterCatOne.setName("cat one");
        shelterCatOne.setId(7771L);
        Shelter shelterCatTwo = new Shelter();
        shelterCatTwo.setName("cat two");
        shelterCatTwo.setId(22L);
        Mockito.when(shelterServiceMock.getSheltersByType(PetType.CAT))
                .thenReturn(List.of(shelterCatOne, shelterCatTwo));
        Mockito.when(shelterServiceMock.getSheltersByType(PetType.DOG))
                .thenReturn(Collections.emptyList());

        Method fillSheltersChooseMenuMethod = callbackQueryHandler.getClass().getDeclaredMethod("fillSheltersChooseMenu");
        fillSheltersChooseMenuMethod.setAccessible(true);
        fillSheltersChooseMenuMethod.invoke(callbackQueryHandler);

        catsChoseMenu.put(String.valueOf(shelterCatOne.getId()), shelterCatOne.getName());
        catsChoseMenu.put(String.valueOf(shelterCatTwo.getId()), shelterCatTwo.getName());

        callbackQueryHandler.handle(user, chat, "7771");
        Mockito.verify(telegramBotServiceMock, Mockito.times(1))
                .sendMessage(chat.id(), CallbackData.CATS.getDescription(),
                        markupHelper.buildMenu(catsMenu), ParseMode.Markdown);
    }

    @Test
    void handleDogsShelterChoseTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Shelter shelterDogOne = new Shelter();
        shelterDogOne.setName("dog one");
        shelterDogOne.setId(101L);
        Shelter shelterDogTwo = new Shelter();
        shelterDogTwo.setName("dog two");
        shelterDogTwo.setId(202L);
        Mockito.when(shelterServiceMock.getSheltersByType(PetType.CAT))
                .thenReturn(Collections.emptyList());
        Mockito.when(shelterServiceMock.getSheltersByType(PetType.DOG))
                .thenReturn(List.of(shelterDogOne, shelterDogTwo));

        Method fillSheltersChooseMenuMethod = callbackQueryHandler.getClass().getDeclaredMethod("fillSheltersChooseMenu");
        fillSheltersChooseMenuMethod.setAccessible(true);
        fillSheltersChooseMenuMethod.invoke(callbackQueryHandler);

        dogsChoseMenu.put(String.valueOf(shelterDogOne.getId()), shelterDogOne.getName());
        dogsChoseMenu.put(String.valueOf(shelterDogTwo.getId()), shelterDogTwo.getName());

        callbackQueryHandler.handle(user, chat, "202");

        Mockito.verify(telegramBotServiceMock, Mockito.times(1))
                .sendMessage(chat.id(), CallbackData.DOGS.getDescription(),
                        markupHelper.buildMenu(dogsMenu), ParseMode.Markdown);
    }

    @Test
    void handleResetShelterChoseTest() {
        callbackQueryHandler.handle(user, chat, CallbackData.RESET_SHELTER.getTitle());
        Mockito.verify(userServiceMock, Mockito.times(1)).
                resetSelectedShelterId(chat.id());
        Mockito.verify(telegramBotServiceMock, Mockito.times(1)).
                sendMessage(chat.id(), "Выберите новый приют:", markupHelper.buildMenu(mainMenu), ParseMode.Markdown);
    }

    @SuppressWarnings("unchecked")
    @Test
    void handleContactsTest() throws NoSuchFieldException, IllegalAccessException {
        callbackQueryHandler.handle(user, chat, CallbackData.CONTACTS.getTitle());
        ArgumentCaptor<ReplyKeyboardMarkup> replyKeyboardMarkupArgumentCaptor = ArgumentCaptor.forClass(ReplyKeyboardMarkup.class);
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ParseMode> parseModeArgumentCaptor = ArgumentCaptor.forClass(ParseMode.class);
        Mockito.verify(telegramBotServiceMock, Mockito.times(1)).
                sendContact(longArgumentCaptor.capture(), stringArgumentCaptor.capture(),
                        replyKeyboardMarkupArgumentCaptor.capture(), parseModeArgumentCaptor.capture());
        assertThat(replyKeyboardMarkupArgumentCaptor.getAllValues()).hasSize(1);
        assertThat(replyKeyboardMarkupArgumentCaptor.getValue()).isExactlyInstanceOf(ReplyKeyboardMarkup.class);
        ReplyKeyboardMarkup replyKeyboardMarkup = replyKeyboardMarkupArgumentCaptor.getValue();
        Field replyKeyboardMarkupField = replyKeyboardMarkup.getClass().getDeclaredField("keyboard");
        replyKeyboardMarkupField.setAccessible(true);
        List<List<KeyboardButton>> keyboard = (List<List<KeyboardButton>>) replyKeyboardMarkupField.get(replyKeyboardMarkup);
        KeyboardButton button = keyboard.get(0).get(0);
        Field keyboardButtonField = button.getClass().getDeclaredField("text");
        keyboardButtonField.setAccessible(true);
        assertThat((String) keyboardButtonField.get(button)).isEqualTo("☎ Отправить свой номер телефона");
        keyboardButtonField = button.getClass().getDeclaredField("request_contact");
        keyboardButtonField.setAccessible(true);
        assertThat((boolean) keyboardButtonField.get(button)).isTrue();
    }

    @Test
    void handleDogsShelterAddressTest() {
        Mockito.when(shelterServiceMock.getShelterById(Mockito.anyLong())).thenReturn(shelter);
        Mockito.when(userServiceMock.getUserByChatId(Mockito.anyLong())).thenReturn(ourUser);
        callbackQueryHandler.handle(user, chat, CallbackData.DOGS_SHELTER_ADDRESS.getTitle());
        Mockito.verify(userServiceMock, Mockito.times(1)).
                getUserByChatId(chat.id());
        Mockito.verify(shelterServiceMock, Mockito.times(1)).
                getShelterById(ourUser.getSelectedShelterId());
        Mockito.verify(telegramBotServiceMock, Mockito.times(1)).
                sendMessage(chat.id(), "\uD83D\uDCCD Адрес приюта для собак: *" + shelter.getAddress() + "*",
                        null, ParseMode.Markdown);
    }

    @Test
    void handleDogsShelterWorkHoursTest() {
        Mockito.when(shelterServiceMock.getShelterById(Mockito.anyLong())).thenReturn(shelter);
        Mockito.when(userServiceMock.getUserByChatId(Mockito.anyLong())).thenReturn(ourUser);
        callbackQueryHandler.handle(user, chat, CallbackData.DOGS_SHELTER_WORK_HOURS.getTitle());
        Mockito.verify(userServiceMock, Mockito.times(1)).
                getUserByChatId(chat.id());
        Mockito.verify(shelterServiceMock, Mockito.times(1)).
                getShelterById(ourUser.getSelectedShelterId());
        Mockito.verify(telegramBotServiceMock, Mockito.times(1)).
                sendMessage(chat.id(), "⌛ Часы работы приюта для собак: *" + shelter.getWorkSchedule() + "*",
                        null, ParseMode.Markdown);
    }

    @Test
    void handleCatsShelterAddressTest() {
        Mockito.when(shelterServiceMock.getShelterById(Mockito.anyLong())).thenReturn(shelter);
        Mockito.when(userServiceMock.getUserByChatId(Mockito.anyLong())).thenReturn(ourUser);
        callbackQueryHandler.handle(user, chat, CallbackData.CATS_SHELTER_ADDRESS.getTitle());
        Mockito.verify(userServiceMock, Mockito.times(1)).
                getUserByChatId(chat.id());
        Mockito.verify(shelterServiceMock, Mockito.times(1)).
                getShelterById(ourUser.getSelectedShelterId());
        Mockito.verify(telegramBotServiceMock, Mockito.times(1)).
                sendMessage(chat.id(), "\uD83D\uDCCD Адрес приюта для кошек: *" + shelter.getAddress() + "*",
                        null, ParseMode.Markdown);
    }

    @Test
    void handleCatsShelterWorkHoursTest() {
        Mockito.when(shelterServiceMock.getShelterById(Mockito.anyLong())).thenReturn(shelter);
        Mockito.when(userServiceMock.getUserByChatId(Mockito.anyLong())).thenReturn(ourUser);
        callbackQueryHandler.handle(user, chat, CallbackData.CATS_SHELTER_WORK_HOURS.getTitle());
        Mockito.verify(userServiceMock, Mockito.times(1)).
                getUserByChatId(chat.id());
        Mockito.verify(shelterServiceMock, Mockito.times(1)).
                getShelterById(ourUser.getSelectedShelterId());
        Mockito.verify(telegramBotServiceMock, Mockito.times(1)).
                sendMessage(chat.id(), "⌛ Часы работы приюта для кошек: *" + shelter.getWorkSchedule() + "*",
                        null, ParseMode.Markdown);
    }


    @DisplayName("Handle with text tests: ")
    @ParameterizedTest
    @MethodSource("provideParamsForHandleWithTextTest")
    void handleWithTextTest(String callbackData, String text, HashMap<String, String> menu, @Nullable ParseMode parseMode) {
        callbackQueryHandler.handle(user, chat, callbackData);
        Mockito.verify(telegramBotServiceMock, Mockito.times(1)).
                sendMessage(chat.id(), text, markupHelper.buildMenu(menu), parseMode);

    }

    @DisplayName("handle with not null parsemode")
    @ParameterizedTest
    @MethodSource("provideParamsForHandleWithTextAndNullParseModeAndNullKeyboard")
    void handleWithTextAndNullParseModeAndNullKeyboardTest(String callbackData, String text) {
        callbackQueryHandler.handle(user, chat, callbackData);
        Mockito.verify(
                        telegramBotServiceMock, Mockito.times(1)).
                sendMessage(chat.id(), text, null, null);

    }

    @DisplayName("handle with pdf send")
    @ParameterizedTest
    @MethodSource("provideParamsForHandleWithPdfSend")
    void handleWithPdfSendTest(String callbackData, String file, String caption) {
        callbackQueryHandler.handle(user, chat, callbackData);
        Mockito.verify(telegramBotServiceMock, Mockito.times(1)).
                sendPDFDocument(chat.id(), file, caption);
    }

    @DisplayName("handle with picture send")
    @ParameterizedTest
    @MethodSource("provideParamsForHandleWithPictureSend")
    void handleWithPictureSendTest(String callbackData, String file, String caption) {
        callbackQueryHandler.handle(user, chat, callbackData);
        Mockito.verify(telegramBotServiceMock, Mockito.times(1)).
                sendPicture(chat.id(), file, caption);
    }

    @DisplayName("handle Report")
    @ParameterizedTest
    @MethodSource("provideParamsForHandleWithNullKeyboardAndNotNullParseMode")
    void handleWithNullKeyboardAndNotNullParseModeTest(String callbackData, String text, ParseMode parseMode) {
        callbackQueryHandler.handle(user, chat, callbackData);
        Mockito.verify(telegramBotServiceMock, Mockito.times(1)).
                sendMessage(chat.id(), text, null, parseMode);
    }
}