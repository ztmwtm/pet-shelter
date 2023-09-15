package com.example.petshelter.service;

import com.example.petshelter.entity.UserReport;
import com.example.petshelter.repository.UserReportRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserReportService {
    private UserReportRepository userReportRepository;

    public void createUserReport(String text) {
        userReportRepository.save(text);
    }

    public void updateUserReport(String fileId) {
        userReportRepository.save(fileId);
    }

    public Optional<UserReport> getUserReport(Long id) {
        return userReportRepository.findById(id);
    }

    public void deleteUserReport(Long id) {
        userReportRepository.deleteById(id);
    }
}
