-- liquibase formatted sql

-- changeset SlavaMarchkov:16

INSERT INTO pets (species, nickname, is_adopted, user_id, shelter_id, day_of_adopt, days_to_adaptation, pet_type)
VALUES  ('сиамская кошка', 'Мурка', null, null, null, null, 0, 'CAT'),
        ('британский кот', 'Билли', null, null, null, null, 0, 'CAT'),
        ('камышовый кот', 'Чарли', null, null, null, null, 0, 'CAT'),
        ('манул', 'Бобби', null, null, null, null, 0, 'CAT'),
        ('бенгальский кот', 'Казанова', null, null, null, null, 0, 'CAT'),
        ('сибирский кот', 'Валенок', null, null, null, null, 0, 'CAT'),
        ('немецкая овчарка', 'Герда', null, null, null, null, 0, 'DOG'),
        ('шпиц', 'Барсик', null, null, null, null, 0, 'DOG'),
        ('ротвейлер', 'Мурзик', null, null, null, null, 0, 'DOG'),
        ('бульдог', 'Джонни', null, null, null, null, 0, 'DOG'),
        ('алабай', 'Дикарь', null, null, null, null, 0, 'DOG'),
        ('борзая', 'Кэтти', null, null, null, null, 0, 'DOG');