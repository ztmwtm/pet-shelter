package com.example.petshelter.util;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public enum Command {

    START("start", "Начать работу");

    private final String title;
    private final String description;
    static Logger LOGGER;
    static {
        try(FileInputStream ins = new FileInputStream("test/log.config")){
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Command.class.getName());
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }

    Command(final String title,
            final String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        try {
            LOGGER.log(Level.INFO, "GetTitle " + title + " Command");
            return title;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error GetTitle Command", e);
        }
        return null;
    }

    public String getDescription() {
        try {
            LOGGER.log(Level.INFO, "GetDescription " + getTitle() + " Command");
            return description;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error GetDescription " + getTitle() + "Command", e);
        }
        return null;
    }
}
