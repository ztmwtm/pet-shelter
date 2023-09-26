package com.example.petshelter.repository;

import com.example.petshelter.entity.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserReportRepository extends JpaRepository<UserReport, Long> {

}
