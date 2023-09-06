package com.example.petshelter.util;

public enum CallbackData {

    CATS("cats", "Выберите, что вы хотите узнать о приюте для кошек:"),
    DOGS("dogs", "Выберите, что вы хотите узнать о приюте для собак:"),

    CATS_INFO("cats.info", "Узнать информацию о приюте"),
    CATS_TAKE("cats.take", "Как взять кошку из приюта"),
    DOGS_INFO("dogs.info", "Узнать информацию о приюте для собак"),
    DOGS_TAKE("dogs.take", "Как взять собаку из приюта"),

    CATS_SHELTER_INFO("cats.shelter.info", "Краткое описание приюта для кошек"),
    CATS_SHELTER_WORK_HOURS("cats.shelter.work_hours", "Расписание работы приюта"),
    CATS_SHELTER_ADDRESS("cats.shelter.address", "Адрес приюта"),
    CATS_SHELTER_HOW_TO_GET("cats.shelter.how_to_get", "Схема проезда"),
    CATS_SHELTER_ENTRY_PASS("cats.shelter.entry_pass", "Оформить пропуск на машину"),
    CATS_SHELTER_SAFETY_RULES("cats.shelter.safety_rules", "Правила техники безопасности на территории приюта"),

    CATS_ADOPTION_SAY_HI_RULES("cats.shelter.say_hi_rules", "Правила знакомства с кошкой"),
    CATS_ADOPTION_DOCUMENTS("cats.shelter.say_hi_rules", "Документы для получения кошки из приюта"),
    CATS_ADOPTION_TRANSPORTATION_RULES("cats.shelter.transportation_rules", "Рекомендации по транспортировке кошки"),
    CATS_ADOPTION_CHILD_HOUSE_RULES("cats.shelter.child_house_rules", "Рекомендации по обустройству дома для котенка"),
    CATS_ADOPTION_ADULT_HOUSE_RULES("cats.shelter.adult_house_rules", "Рекомендации по обустройству дома для взрослой кошки"),
    CATS_ADOPTION_DISABLED_HOUSE_RULES("cats.shelter.disabled_house_rules", "Рекомендации по обустройству дома для кошки с ограниченными возможностями"),
    CATS_ADOPTION_REASONS_FOR_REFUSAL("cats.shelter.reasons_for_refusal", "Причины отказа в получении кошки из приюта"),

    DOGS_ADOPTION_SAY_HI_RULES("dogs.shelter.say_hi_rules", "Правила знакомства с собакой"),
    DOGS_ADOPTION_DOCUMENTS("dogs.shelter.say_hi_rules", "Документы для получения собаки из приюта"),
    DOGS_ADOPTION_TRANSPORTATION_RULES("dogs.shelter.transportation_rules", "Рекомендации по транспортировке собаки"),
    DOGS_ADOPTION_CHILD_HOUSE_RULES("dogs.shelter.child_house_rules", "Рекомендации по обустройству дома для щенка"),
    DOGS_ADOPTION_ADULT_HOUSE_RULES("dogs.shelter.adult_house_rules", "Рекомендации по обустройству дома для взрослой собаки"),
    DOGS_ADOPTION_DISABLED_HOUSE_RULES("dogs.shelter.disabled_house_rules", "Рекомендации по обустройству дома для собаки с ограниченными возможностями"),
    DOGS_ADOPTION_DOG_HANDLER_RULES("dogs.shelter.dog_handler_rules", "Советы кинолога по первичному общению с собакой"),
    DOGS_ADOPTION_DOG_HANDLERS_LIST("dogs.shelter.dog_handlers_list", "Список проверенных кинологов для обращения к ним"),
    DOGS_ADOPTION_REASONS_FOR_REFUSAL("dogs.shelter.reasons_for_refusal", "Причины отказа в получении собаки из приюта"),

    DOGS_SHELTER_INFO("dogs.shelter.info", "Краткое описание приюта для собак"),
    DOGS_SHELTER_WORK_HOURS("dogs.shelter.work_hours", "Расписание работы приюта"),
    DOGS_SHELTER_ADDRESS("dogs.shelter.address", "Адрес приюта"),
    DOGS_SHELTER_HOW_TO_GET("dogs.shelter.how_to_get", "Схема проезда"),
    DOGS_SHELTER_ENTRY_PASS("dogs.shelter.entry_pass", "Оформить пропуск на машину"),
    DOGS_SHELTER_SAFETY_RULES("dogs.shelter.safety_rules", "Правила техники безопасности на территории приюта"),

    CONTACTS("contacts", "Оставить свои контактные данные"),
    REPORT("report", "Прислать отчет о питомце"),
    HELP("help", "Позвать волонтера");

    private final String title;
    private final String description;
    private String fileId = null;

    CallbackData(final String title, final String description) {
        this.title = title;
        this.description = description;
//        this.fileId = fileId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
