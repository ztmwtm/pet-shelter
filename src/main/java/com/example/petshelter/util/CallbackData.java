package com.example.petshelter.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Набор констант с названием кнопок и их описание для получения информации о приютах
 */
@Slf4j
public enum CallbackData {

    CATS("cats", "Выберите, что вы хотите узнать о приюте для кошек:"),
    DOGS("dogs", "Выберите, что вы хотите узнать о приюте для собак:"),

    CATS_SHELTER_CHOSE("cats.chose", "Выберите любой из приютов для кошек:"),
    DOGS_SHELTER_CHOSE("dogs.chose", "Выберите любой из приютов для собак:"),
    USER_CHOOSE("user.choose", "Выберите пользователя:"),
    PET_CHOOSE("pet.choose", "Выберите питомца"),
    RESET_SHELTER("reset.shelter", "\u274C Выбрать другой приют."),

    CATS_INFO("cats.info", "ℹ Узнать информацию о приюте"),
    CATS_TAKE("cats.take", "\uD83D\uDC08 Как взять кошку из приюта"),
    DOGS_INFO("dogs.info", "ℹ Узнать информацию о приюте"),
    DOGS_TAKE("dogs.take", "\uD83D\uDC15 Как взять собаку из приюта"),

    CATS_SHELTER_INFO("cats.shelter.info", "\uD83D\uDC08 Краткое описание приюта для кошек"),
    CATS_SHELTER_WORK_HOURS("cats.shelter.work_hours", "⌛ Расписание работы приюта"),
    CATS_SHELTER_ADDRESS("cats.shelter.address", "\uD83D\uDCCD Адрес приюта"),
    CATS_SHELTER_HOW_TO_GET("cats.shelter.how_to_get", "\uD83D\uDE97 Схема проезда"),
    CATS_SHELTER_ENTRY_PASS("cats.shelter.entry_pass", "\uD83D\uDEC2 Оформить пропуск на машину"),
    CATS_SHELTER_SAFETY_RULES("cats.shelter.safety_rules", "\uD83D\uDC77 Правила техники безопасности на территории приюта"),

    CATS_ADOPTION_SAY_HI_RULES("cats.shelter.say_hi_rules", "\uD83D\uDC4B Правила знакомства с кошкой"),
    CATS_ADOPTION_DOCUMENTS("cats.shelter.documents", "\uD83D\uDCBC Документы для получения кошки из приюта"),
    CATS_ADOPTION_TRANSPORTATION_RULES("cats.shelter.transportation_rules", "\uD83D\uDE8C Рекомендации по транспортировке кошки"),
    CATS_ADOPTION_CHILD_HOUSE_RULES("cats.shelter.child_house_rules", "\uD83C\uDFE0 Рекомендации по обустройству дома для котенка"),
    CATS_ADOPTION_ADULT_HOUSE_RULES("cats.shelter.adult_house_rules", "\uD83C\uDFE0 Рекомендации по обустройству дома для взрослой кошки"),
    CATS_ADOPTION_DISABLED_HOUSE_RULES("cats.shelter.disabled_house_rules", "\uD83C\uDFE0 Рекомендации по обустройству дома для кошки с ограниченными возможностями"),
    CATS_ADOPTION_REASONS_FOR_REFUSAL("cats.shelter.reasons_for_refusal", "\uD83D\uDED1 Причины отказа в получении кошки из приюта"),

    DOGS_ADOPTION_SAY_HI_RULES("dogs.shelter.say_hi_rules", "\uD83D\uDC4B Правила знакомства с собакой"),
    DOGS_ADOPTION_DOCUMENTS("dogs.shelter.documents", "\uD83D\uDCBC Документы для получения собаки из приюта"),
    DOGS_ADOPTION_TRANSPORTATION_RULES("dogs.shelter.transportation_rules", "\uD83D\uDE8C Рекомендации по транспортировке собаки"),
    DOGS_ADOPTION_CHILD_HOUSE_RULES("dogs.shelter.child_house_rules", "\uD83C\uDFE0 Рекомендации по обустройству дома для щенка"),
    DOGS_ADOPTION_ADULT_HOUSE_RULES("dogs.shelter.adult_house_rules", "\uD83C\uDFE0 Рекомендации по обустройству дома для взрослой собаки"),
    DOGS_ADOPTION_DISABLED_HOUSE_RULES("dogs.shelter.disabled_house_rules", "\uD83C\uDFE0 Рекомендации по обустройству дома для собаки с ограниченными возможностями"),
    DOGS_ADOPTION_DOG_HANDLER_RULES("dogs.shelter.dog_handler_rules", "\uD83D\uDC81 Советы кинолога по первичному общению с собакой"),
    DOGS_ADOPTION_DOG_HANDLERS_LIST("dogs.shelter.dog_handlers_list", "\uD83C\uDD8E Список проверенных кинологов для обращения к ним"),
    DOGS_ADOPTION_REASONS_FOR_REFUSAL("dogs.shelter.reasons_for_refusal", "\uD83D\uDED1 Причины отказа в получении собаки из приюта"),

    DOGS_SHELTER_INFO("dogs.shelter.info", "\uD83D\uDC15 Краткое описание приюта для собак"),
    DOGS_SHELTER_WORK_HOURS("dogs.shelter.work_hours", "⌛ Расписание работы приюта"),
    DOGS_SHELTER_ADDRESS("dogs.shelter.address", "\uD83D\uDCCD Адрес приюта"),
    DOGS_SHELTER_HOW_TO_GET("dogs.shelter.how_to_get", "\uD83D\uDE97 Схема проезда"),
    DOGS_SHELTER_ENTRY_PASS("dogs.shelter.entry_pass", "\uD83D\uDEC2 Оформить пропуск на машину"),
    DOGS_SHELTER_SAFETY_RULES("dogs.shelter.safety_rules", "\uD83D\uDC77 Правила техники безопасности на территории приюта"),

    CONTACTS("contacts", "☎ Оставить свои контактные данные"),
    REPORT("report", "✉ Прислать отчет о питомце"),
    HELP("help", "❓ Позвать волонтера"),

    START_VOLUNTEER("start.volunteer", "Меню"),
    ADD_ADOPTER("add.adopter", "Добавить усыновителя"),
    CHECK_REPORTS("check.reports", "Проверить отчеты"),
    EXTEND_TRIAL("extend.trial","Продлить испытательный срок"),
    KEEP_ANIMAL("keep.animal", "Оставить животное у хозяина"),
    FAIL_TRIAL("fail.trial", "Вернуть животное в приют с испытательного срока" );
    ACCEPT_REPORT("accept.report", "Принять отчет"),
    REJECT_REPORT("reject.report", "Отклонить отчет");


    private final String title;
    private final String description;

    CallbackData(final String title, final String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        try {
//            log.info("GetTitle CallbackData");
            return title;
        } catch (Exception e) {
            log.error(e.getMessage() + "Error GetTitle CallbackData");
        }
        return null;
    }

    public String getDescription() {
        try {
//            log.info("GetDescription CallbackData");
            return description;
        } catch (Exception e) {
            log.error(e.getMessage() + "Error GetDescription CallbackData");
        }
        return null;
    }

}
