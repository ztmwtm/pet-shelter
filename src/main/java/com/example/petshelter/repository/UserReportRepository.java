package com.example.petshelter.repository;

import com.example.petshelter.entity.UserReport;
import com.example.petshelter.util.UserReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Long> {

    UserReport getUserReportById(Long id);
    List<UserReport> getUserReportsByUser_id(Long id);
    List<UserReport> getUserReportsByPet_id(Long id);

    List<UserReport>  getUserReportByUser_idAndStatus(Long id, UserReportStatus status);

    @Query(value = "SELECT * FROM user_reports ur WHERE ur.status = 'CREATED'", nativeQuery = true)
    Optional<UserReport> getUserReportByUser_idAndStatusCreated(Long userId);

    List<UserReport> getUserReportByStatus(UserReportStatus status);

}
