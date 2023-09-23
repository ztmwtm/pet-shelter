package com.example.petshelter.service;

import com.example.petshelter.entity.UserReportPhoto;
import com.example.petshelter.repository.UserReportPhotoRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Connection;
import java.util.Optional;

@Service
public class UserReportPhotoService {
    private UserReportPhotoRepository userReportPhotoRepository;
    private static final String URL = "jdbc:mysql://178.21.11.17:3306/pet_shelter_db";
    private static final String USER = "zrazhevskiy";
    private static final String PASSWORD = "5T!65gr2";
    private static Connection connection;

    public void createUserReportPhoto(File file) {
        userReportPhotoRepository.save(String.valueOf(file));
    }

    public void updateUserReportPhoto(String fileId) {
        userReportPhotoRepository.save(fileId);
    }

    public Optional<UserReportPhoto> getUserReportPhoto(Long id) {
        return userReportPhotoRepository.findById(id);
    }

    public void deleteUserReportPhoto(Long id) {
        userReportPhotoRepository.deleteById(id);
    }
}
