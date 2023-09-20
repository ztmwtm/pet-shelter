-- liquibase formatted sql

-- changeset SlavaMarchkov:16

INSERT INTO pet_shelter_db.shelters (name, address, phone_number, work_schedule, latitude, longitude, type) VALUES ('Питомник для котов', 'г. Астана, ул. Жумабаева 32/7', '87013635997', '07:00 - 21:00', 51.1408, 71.491, 'CAT');
INSERT INTO pet_shelter_db.shelters (name, address, phone_number, work_schedule, latitude, longitude, type) VALUES ('Питомник для кошек', 'ул. Алихана Бокейханова 27, Astana 020000, Kazakhstan', '87172642200', '09:00 - 20:00', 51.0884, 71.4323, 'CAT');
INSERT INTO pet_shelter_db.shelters (name, address, phone_number, work_schedule, latitude, longitude, type) VALUES ('Питомник для собак', 'Шалкиіз жырау 70, Astana 020000, Kazakhstan', '87757434800', '10:00 - 18:00', 51.1167, 71.2543, 'DOG');
INSERT INTO pet_shelter_db.shelters (name, address, phone_number, work_schedule, latitude, longitude, type) VALUES ('Питомник для щенков', 'Qorghalzhyn Hwy 28, Astana 020000, Kazakhstan', '87017772734', '08:00 - 19:00', 51.1497, 71.3516, 'DOG');
