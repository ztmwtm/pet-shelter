package com.example.petshelter.util;

import com.example.petshelter.listener.TelegramBotUpdatesListener;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public enum CallbackData {

    CATS("cats", "Выберите, что вы хотите узнать о приюте для кошек:"),
    DOGS("dogs", "Выберите, что вы хотите узнать о приюте для собак:"),

    CATS_INFO("cats.info", "Узнать информацию о приюте"),
    CATS_TAKE("cats.take", "Как взять кошку из приюта"),
    DOGS_INFO("dogs.info", "Узнать информацию о приюте для собак"),
    DOGS_TAKE("dogs.take", "Как взять собаку из приюта"),

    REPORT("report", "Прислать отчет о питомце"),
    HELP("help", "Позвать волонтера");

    private final String title;
    private final String description;
    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("test/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(CallbackData.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }

    CallbackData(final String title, final String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        try {
            LOGGER.log(Level.INFO, "GetTitle " + title + " CallbackData");
            return title;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error GetTitle CallbackData", e);
        }
        return null;
    }

    public String getDescription() {
        try {
            LOGGER.log(Level.INFO, "GetDescription " + getTitle() + " CallbackData");
            return description;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error GetDescription " + getTitle() + "CallbackData", e);
        }
        return null;
    }

}
