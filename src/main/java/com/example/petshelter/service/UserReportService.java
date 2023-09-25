package com.example.petshelter.service;

import com.example.petshelter.entity.UserReport;
import com.example.petshelter.exception.UserReportNotFoundException;
import com.example.petshelter.repository.UserReportRepository;
import com.example.petshelter.util.UserReportStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserReportService {
    private final UserReportRepository userReportRepository;
    private final Logger logger = LoggerFactory.getLogger(UserReportService.class);

    public UserReportService(UserReportRepository userReportRepository) {
        this.userReportRepository = userReportRepository;
    }

    public UserReport addUserReport(UserReport userReport) {
        logger.info("Was called method to add user report");
        return userReportRepository.save(userReport);
    }

    public UserReport updateUserReport(Long id, UserReport userReport) {
        logger.info("Was called method to update userReport with id {}", id);
        UserReport oldUserReport = userReportRepository.findById(id).orElseThrow(() -> new UserReportNotFoundException(id));
        oldUserReport.setPetDiet(userReport.getPetDiet());
        oldUserReport.setHealth(userReport.getHealth());
        oldUserReport.setBehavior(userReport.getBehavior());
        oldUserReport.setUser(userReport.getUser());
        oldUserReport.setPet(userReport.getPet());
        oldUserReport.setStatus(userReport.getStatus());

        return userReportRepository.save(oldUserReport);
    }

    public UserReport getUserReportById(Long id) {
        logger.info("Was called method to get userReport with id {}", id);
        return userReportRepository.findById(id).orElseThrow(() -> new UserReportNotFoundException(id));
    }

    public Collection<UserReport> getAllUserReports() {
        logger.info("Was called method to get all userReports");
        return userReportRepository.findAll();
    }

    public UserReport deleteUserReportById(Long id) {
        logger.info("Was called method to delete userReport with id {}", id);
        UserReport userReport = userReportRepository.findById(id)
                .orElseThrow(() -> new UserReportNotFoundException(id));
        userReportRepository.deleteById(id);
        return userReport;
    }

    public List<UserReport> getUserReportsByUserId(Long id) {
        return userReportRepository.getUserReportsByUser_id(id);
    }

    public List<UserReport> getUserReportByUserIdAndStatus(Long id, UserReportStatus status) {
        return userReportRepository.getUserReportByUser_idAndStatus(id, status);
    }
    public List<UserReport> getUserReportsByPetId(Long id) {
        return userReportRepository.getUserReportsByPet_id(id);
    }

    public UserReport  getUserReportByUserIdAndStatusCreated(Long userId) {
        logger.info("Was called method to get report by userId {} and report status 'CREATED'", userId);
        return userReportRepository.getUserReportByUser_idAndStatusCreated(userId).orElse(null);
    }

    public List<UserReport> getUserReportByStatus(UserReportStatus status) {
        return userReportRepository.getUserReportByStatus(status);
    }
}
