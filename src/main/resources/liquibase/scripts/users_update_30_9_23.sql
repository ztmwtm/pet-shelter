-- liquibase formatted sql

-- changeset zrazhevskiy:21

alter table users
    add column selected_pet_id BIGINT;

-- changeset zrazhevskiy:22

alter table users
    modify column active_menu ENUM (
        'CATS',
        'DOGS',
        'CATS_SHELTER_CHOSE',
        'DOGS_SHELTER_CHOSE',
        'RESET_SHELTER',
        'CATS_INFO',
        'CATS_TAKE',
        'DOGS_INFO',
        'DOGS_TAKE',
        'CATS_SHELTER_INFO',
        'CATS_SHELTER_WORK_HOURS',
        'CATS_SHELTER_ADDRESS',
        'CATS_SHELTER_HOW_TO_GET',
        'CATS_SHELTER_ENTRY_PASS',
        'CATS_SHELTER_SAFETY_RULES',
        'CATS_ADOPTION_SAY_HI_RULES',
        'CATS_ADOPTION_DOCUMENTS',
        'CATS_ADOPTION_TRANSPORTATION_RULES',
        'CATS_ADOPTION_CHILD_HOUSE_RULES',
        'CATS_ADOPTION_ADULT_HOUSE_RULES',
        'CATS_ADOPTION_DISABLED_HOUSE_RULES',
        'CATS_ADOPTION_REASONS_FOR_REFUSAL',
        'DOGS_ADOPTION_SAY_HI_RULES',
        'DOGS_ADOPTION_DOCUMENTS',
        'DOGS_ADOPTION_TRANSPORTATION_RULES',
        'DOGS_ADOPTION_CHILD_HOUSE_RULES',
        'DOGS_ADOPTION_ADULT_HOUSE_RULES',
        'DOGS_ADOPTION_DISABLED_HOUSE_RULES',
        'DOGS_ADOPTION_DOG_HANDLER_RULES',
        'DOGS_ADOPTION_DOG_HANDLERS_LIST',
        'DOGS_ADOPTION_REASONS_FOR_REFUSAL',
        'DOGS_SHELTER_INFO',
        'DOGS_SHELTER_WORK_HOURS',
        'DOGS_SHELTER_ADDRESS',
        'DOGS_SHELTER_HOW_TO_GET',
        'DOGS_SHELTER_ENTRY_PASS',
        'DOGS_SHELTER_SAFETY_RULES',
        'CONTACTS',
        'REPORT',
        'HELP',
        'START_VOLUNTEER',
        'ADD_ADOPTER',
        'CHECK_REPORTS',
        'EXTEND_TRIAL',
        'FAIL_TRIAL',
        'KEEP_ANIMAL',
        'MAIN_MENU',
        'MAIN_MENU_WITHOUT_CHOSE',
        'VOLUNTEER_MENU',
        'CATS_MENU',
        'DOGS_MENU',
        'CATS_INFO_MENU',
        'DOGS_INFO_MENU',
        'CATS_TAKE_MENU',
        'DOGS_TAKE_MENU',
        'FILE_MAPPER'
    );
