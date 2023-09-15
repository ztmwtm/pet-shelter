package com.example.petshelter.service;

import com.pengrad.telegrambot.model.Document;
import com.pengrad.telegrambot.model.PhotoSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class UserReportPhotoService {

    private final static Logger logger = LoggerFactory.getLogger("UserReportPhotoFileInfoService.class");
    @Value("${petshelter.files.user.report.photo.dir.path}")
    private String USER_REPORTS_PHOTO_DIR_PATH;
    private final TelegramBotService telegramBotService;

    public UserReportPhotoService(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }


    public File uploadsPhotos(Long userReportId, PhotoSize... resources) {
        PhotoSize photoSize = Arrays.stream(resources).max(Comparator.comparingLong(PhotoSize::fileSize))
                .orElseThrow();
        File ourFile = uploadFile(photoSize.fileId(), photoSize.fileUniqueId());
        //TODO добавить занесение id репорта
        //TODO добавить занесение файла например userReportService.updatePhoto(userReportId, ourFile)

        return ourFile;
    }


    public File uploadsDocuments(Long userReportId, Document document) {
        File ourFile = uploadFile(document.fileId(), document.fileUniqueId());
        //TODO добавить занесение id репорта
        //TODO добавить занесение файла например userReportService.updatePhoto(userReportId, ourFile)
        return ourFile;

    }

    private File uploadFile(String fileId, String fileUniqId) {
        Path ourFilePath = null;
        URL tgFileURL = null;
        try {
            tgFileURL = telegramBotService.getFileURL(fileId);
            tgFileURL = new URL(tgFileURL.getProtocol(), tgFileURL.getHost(), 443, tgFileURL.getFile());
            ourFilePath = Path.of(USER_REPORTS_PHOTO_DIR_PATH, fileUniqId +
                    tgFileURL.toString().substring(tgFileURL.toString().lastIndexOf('.')));
        } catch (MalformedURLException e) {
            logger.error(e.toString());
        }
        try (InputStream is = Objects.requireNonNull(tgFileURL).openStream()) {
            Files.createDirectories(Objects.requireNonNull(ourFilePath));
            Files.copy(is, Objects.requireNonNull(ourFilePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return Objects.requireNonNull(ourFilePath).toFile();
    }
}
