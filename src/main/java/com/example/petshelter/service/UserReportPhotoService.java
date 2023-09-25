package com.example.petshelter.service;

import com.example.petshelter.entity.UserReport;
import com.example.petshelter.entity.UserReportPhoto;
import com.example.petshelter.repository.UserReportPhotoRepository;
import com.example.petshelter.repository.UserReportRepository;
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
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

@Service
public class UserReportPhotoService {

    private final Logger logger = LoggerFactory.getLogger("UserReportPhotoFileInfoService.class");
    @Value("${petshelter.files.user.report.photo.dir.path}")
    private String userReportsPhotoDirPath;
    private final TelegramBotService telegramBotService;
    private final UserReportPhotoRepository userReportPhotoRepository;
    private final UserReportRepository userReportRepository;

    public UserReportPhotoService(TelegramBotService telegramBotService, UserReportPhotoRepository userReportPhotoRepository, UserReportRepository userReportRepository) {
        this.telegramBotService = telegramBotService;
        this.userReportPhotoRepository = userReportPhotoRepository;
        this.userReportRepository = userReportRepository;
    }

    public File uploadsPhotos(Long userReportId, PhotoSize... resources) {
        logger.info("Was called method to upload photo");
        PhotoSize photoSize = Arrays.stream(resources).max(Comparator.comparingLong(PhotoSize::fileSize))
                .orElseThrow();
        File ourFile = uploadFile(photoSize.fileId(), photoSize.fileUniqueId());

        saveToRepository(userReportId, ourFile);

        return ourFile;
    }

    public File uploadsDocuments(Long userReportId, Document document) {
        logger.info("Was called method to upload document");
        File ourFile = uploadFile(document.fileId(), document.fileUniqueId());

        saveToRepository(userReportId, ourFile);

        return ourFile;

    }

    private File uploadFile(String fileId, String fileUniqId) {
        logger.info("Was called method to upload file");
        Path ourFilePath = null;
        URL tgFileURL = null;
        try {
            tgFileURL = telegramBotService.getFileURL(fileId);
            tgFileURL = new URL(tgFileURL.getProtocol(), tgFileURL.getHost(), 443, tgFileURL.getFile());
            ourFilePath = Path.of(userReportsPhotoDirPath, fileUniqId +
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

    public UserReportPhoto findUserReportPhoto(Long userReportId) {
        logger.info("Was called method to find User Report Photo for User Report with id {}", userReportId);
        return userReportPhotoRepository.getUserReportPhotoByUserReport_id(userReportId).orElse(null);
    }

    private void saveToRepository(Long userReportId, File ourFile){
        logger.info("Was called method to save file to repository");
        UserReport userReport = userReportRepository.getUserReportById(userReportId);
        UserReportPhoto oldUserReportPhoto = findUserReportPhoto(userReportId);

        if (oldUserReportPhoto==null) {
            UserReportPhoto userReportPhoto = new UserReportPhoto();
            userReportPhoto.setUserReport(userReport);
            userReportPhoto.setFilePath(ourFile.getPath());

            userReportPhotoRepository.save(userReportPhoto);
        } else {
            oldUserReportPhoto.setUserReport(userReport);
            oldUserReportPhoto.setFilePath(ourFile.getPath());

            userReportPhotoRepository.save(oldUserReportPhoto);
        }
    }


}
