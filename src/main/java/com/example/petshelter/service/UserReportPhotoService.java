package com.example.petshelter.service;

import com.example.petshelter.entity.UserReportPhoto;
import com.example.petshelter.repository.UserReportPhotoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserReportPhotoService {
    private UserReportPhotoRepository userReportPhotoRepository;

    public void createUserReportPhoto(String fileId) {
        userReportPhotoRepository.save(fileId);
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
