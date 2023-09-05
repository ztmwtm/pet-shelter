package com.example.petshelter.handler;

import com.example.petshelter.helper.MarkupHelper;
import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.util.CallbackData;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.ParseMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Slf4j
@Component
public class CallbackQueryHandler {

    private final Map<CallbackData, BiConsumer<User, Chat>> queryExecutors = new HashMap<>();
    private final TelegramBotService telegramBotService;
    private final MarkupHelper markupHelper;
    private final Map<String, String> catsMenu = new LinkedHashMap<>();
    private final Map<String, String> dogsMenu = new LinkedHashMap<>();

    {
        queryExecutors.put(CallbackData.CATS, this::handleCats);
        queryExecutors.put(CallbackData.DOGS, this::handleDogs);
        queryExecutors.put(CallbackData.CATS_INFO, this::handleCatsInfo);
        queryExecutors.put(CallbackData.DOGS_INFO, this::handleDogsInfo);
        queryExecutors.put(CallbackData.CATS_TAKE, this::handleCatsTake);
        queryExecutors.put(CallbackData.DOGS_TAKE, this::handleDogsTake);
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
    }

    public CallbackQueryHandler(final TelegramBotService telegramBotService, final MarkupHelper markupHelper) {
        this.telegramBotService = telegramBotService;
        this.markupHelper = markupHelper;
        log.info("Construct CallbackQueryHandler ");
    }

    public void handle(User user, Chat chat, String data) {
        try {
            CallbackData[] queries = CallbackData.values();
            for (CallbackData query : queries) {
                if ((query.getTitle()).equals(data)) {
                    queryExecutors.get(query).accept(user, chat);
                    break;
                }
            }
            log.info("Hendel CallbackOueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + " Error Hendel CallbackQueryHandler");
        }
    }

    private void handleDogs(User user, Chat chat) {
        try {
            String text = CallbackData.DOGS.getDescription();
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(dogsMenu), null);
            log.info("HendelDogs CallbackOueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleDogs CallbackQueryHandler");
        }
    }

    private void handleCats(User user, Chat chat) {
        try {
            String text = CallbackData.CATS.getDescription();
            telegramBotService.sendMessage(chat.id(), text, markupHelper.buildMenu(catsMenu), null);
            log.info("HendelCats CallbackOueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleCats CallbackQueryHandler");
        }
    }

    private void handleCatsInfo(User user, Chat chat) {
        try {
            String name = user.firstName();
            String text = name + """
                **Этап 1. Консультация с новым пользователем - КОШКИ**\s

                *На данном этапе бот должен давать вводную информацию о приюте: где он находится, как и когда работает, какие правила пропуска на территорию приюта, правила нахождения внутри и общения с животным. Функционал приюта для кошек и для собак идентичный, но информация внутри будет разной, так как приюты находятся в разном месте и у них разные ограничения и правила нахождения с животными.*\s

                - Бот приветствует пользователя.
                - Бот может рассказать о приюте.
                - Бот может выдать расписание работы приюта и адрес, схему проезда.
                - Бот может выдать контактные данные охраны для оформления пропуска на машину.
                - Бот может выдать общие рекомендации о технике безопасности на территории приюта.
                - Бот может принять и записать контактные данные для связи.
                - Если бот не может ответить на вопросы клиента, то можно позвать волонтера.""";
            telegramBotService.sendMessage(chat.id(), text, null, ParseMode.Markdown);
            log.info("HendelCatsInfo CallbackOueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleCatsInfo CallbackQueryHandler");
        }
    }

    private void handleDogsInfo(User user, Chat chat) {
        try {
            String name = user.firstName();
            String text = name + """
                **Этап 1. Консультация с новым пользователем - СОБАКИ**\s

                *На данном этапе бот должен давать вводную информацию о приюте: где он находится, как и когда работает, какие правила пропуска на территорию приюта, правила нахождения внутри и общения с животным. Функционал приюта для кошек и для собак идентичный, но информация внутри будет разной, так как приюты находятся в разном месте и у них разные ограничения и правила нахождения с животными.*\s

                - Бот приветствует пользователя.
                - Бот может рассказать о приюте.
                - Бот может выдать расписание работы приюта и адрес, схему проезда.
                - Бот может выдать контактные данные охраны для оформления пропуска на машину.
                - Бот может выдать общие рекомендации о технике безопасности на территории приюта.
                - Бот может принять и записать контактные данные для связи.
                - Если бот не может ответить на вопросы клиента, то можно позвать волонтера.""";
            telegramBotService.sendMessage(chat.id(), text, null, ParseMode.Markdown);
            log.info("HendelDogsInfo CallbackOueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleDogsInfo CallbackQueryHandler");
        }
    }

    private void handleCatsTake(User user, Chat chat) {
        try {
            String name = user.firstName();
            String text = name + """
                **Этап 2. Консультация с потенциальным хозяином КОШКИ из приюта**\s
                                                                                            
                *На данном этапе бот помогает потенциальным усыновителям животного из приюта разобраться с бюрократическими (оформление договора) и бытовыми (как подготовиться к жизни с животным) вопросами.*\s
                                
                *Основная задача: дать максимально полную информацию о том, как предстоит подготовиться человеку ко встрече с новым членом семьи.*\s
                                
                - Бот приветствует пользователя.
                - Бот может выдать правила знакомства с животным до того, как забрать его из приюта.
                - Бот может выдать список документов, необходимых для того, чтобы взять животное из приюта.
                - Бот может  выдать список рекомендаций по транспортировке животного.
                - Бот может  выдать список рекомендаций по обустройству дома для щенка/котенка.
                - Бот может  выдать список рекомендаций по обустройству дома для взрослого животного.
                - Бот может  выдать список рекомендаций по обустройству дома для животного с ограниченными возможностями (зрение, передвижение).
                - Бот может выдать советы кинолога по первичному общению с собакой *(неактуально для кошачьего приюта, реализовать только для приюта для собак).*
                - Бот может выдать рекомендации по проверенным кинологам для дальнейшего обращения к ним *(неактуально для кошачьего приюта, реализовать только для приюта для собак).*
                - Бот может выдать список причин, почему могут отказать и не дать забрать собаку из приюта.
                - Бот может принять и записать контактные данные для связи.
                - Если бот не может ответить на вопросы клиента, то можно позвать волонтера.""";
            telegramBotService.sendMessage(chat.id(), text, null, ParseMode.Markdown);
            log.info("HendelCatsTake CallbackOueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleCatsTake CallbackQueryHandler");
        }
    }

    private void handleDogsTake(User user, Chat chat) {
        try {
            String name = user.firstName();
            String text = name + """
                **Этап 2. Консультация с потенциальным хозяином СОБАКИ из приюта**\s
                                                                                            
                *На данном этапе бот помогает потенциальным усыновителям животного из приюта разобраться с бюрократическими (оформление договора) и бытовыми (как подготовиться к жизни с животным) вопросами.*\s
                                
                *Основная задача: дать максимально полную информацию о том, как предстоит подготовиться человеку ко встрече с новым членом семьи.*\s
                                
                - Бот приветствует пользователя.
                - Бот может выдать правила знакомства с животным до того, как забрать его из приюта.
                - Бот может выдать список документов, необходимых для того, чтобы взять животное из приюта.
                - Бот может  выдать список рекомендаций по транспортировке животного.
                - Бот может  выдать список рекомендаций по обустройству дома для щенка/котенка.
                - Бот может  выдать список рекомендаций по обустройству дома для взрослого животного.
                - Бот может  выдать список рекомендаций по обустройству дома для животного с ограниченными возможностями (зрение, передвижение).
                - Бот может выдать советы кинолога по первичному общению с собакой *(неактуально для кошачьего приюта, реализовать только для приюта для собак).*
                - Бот может выдать рекомендации по проверенным кинологам для дальнейшего обращения к ним *(неактуально для кошачьего приюта, реализовать только для приюта для собак).*
                - Бот может выдать список причин, почему могут отказать и не дать забрать собаку из приюта.
                - Бот может принять и записать контактные данные для связи.
                - Если бот не может ответить на вопросы клиента, то можно позвать волонтера.""";
            telegramBotService.sendMessage(chat.id(), text, null, ParseMode.Markdown);
            log.info("HendelDogsTace CallbackOueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleDogsTake CallbackQueryHandler");
        }
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
            log.info("HendelReport CallbackOueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HandleReport CallbackQueryHandler");
        }
    }

    private void handleVolunteerHelp(User user, Chat chat) {
        try {
            String text = """
                Павел Маеров
                28.08.23 03:06
                При обсуждении задания в группе возникли определенные вопросы. Скажите, пожалуйста:

                1.В каком виде бот может позвать волонтера? Как волонтер получает этот вызов? После этого вызова, сообщения волонтера должны появиться в чате пользователя с ботом? Или бот пересылает волонтеру контакт пользователя и волонтер начинает отдельный чат с пользователем?
                1) Тут как реализуете. Простой вариант, что волонтеру приходит сообщение, что нужно связаться с каким-то пользователем. Более сложный, это "создается" чат волонтера и пользователя. Это не отдельный чат, а прям в боте должно происходить.\s

                2.Сходный вопрос: Как, в случае неполучения отчета, бот создаст запрос волонтеру на связь с усыновителем. Что такое запрос - письмо, звонок, сообщение в специальном чате?
                2) Это может выглядеть как сообщение волонтеру о не сданном отчете какого-то пользователя

                3.Поскольку волонтер должен общаться с пользователем, он (волонтер) должен быть подключен к базе данных. Например, чтобы продлевать испытательный срок, реагировать на отчеты и пр. Должны ли мы придумывать способ управления базой для волонтера? какой-то API (контроллер)? Или считать, что волонтер будет сразу к базе подключен и этот интерфейс в проект не входит?
                3) Да, в идеале сделать нужные кнопки у волонтера, он ведь по сути общается с пользователем через бота, а бот подключен ко всему нужному

                4.В задании ничего не сказано о животных приюта. Надо ли расширять таблицы базы и функции бота для работы с конкретными животными.\s
                Или можно получить пятерку и без этого? )
                4) В данном случае у нас 2 вида животных, можно продумать архитектуру так, чтобы в будущем было легко добавлять новых. Делать этого не придется, но продумать на будущее хорошо бы. Допустим, через год придет заказчик и скажет "теперь нужно добавить зебру, жирафа, слона и енота в приют". Чтобы в таком случае не пришлось переписывать половину приложения. Это не обязательное условие, скорее дополнитеное)

                5.В задании сказано: Для усыновителей кошачьего приюта база одна, для собачьего приюта — другая. Подразумеваются именно разные базы? Не таблицы? Если да, то надо полагать, что пользователи бота будут жить в третьей базе?
                5) Тут имеются ввиду разные таблицы, база данных одна.


                6. А каким образом волонтер будет заполнять базы данных? Для этого должно быть стороннее приложение, на пример сайт? В тз сказано, что в базу усыновители попадают при помощи волонтера.\s
                6) Волонтер работает также через бот, можно сказать, что для него бот имеет "особый" функционал. Волонтер через бота может вносить данные пользователей, таким образом сохраняя их в базе.""";
            telegramBotService.sendMessage(chat.id(), text, null, ParseMode.Markdown);
            log.info("HendelVolunteerHelp CallbackOueryHandler");
        } catch (Exception e) {
            log.error(e.getMessage() + "Error HendleVolunteerHelp CallbackQueryHendler");
        }
    }
}