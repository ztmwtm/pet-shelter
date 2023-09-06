package com.example.petshelter.repository;

import com.example.petshelter.entity.UserReportPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReportPhotoRepository extends JpaRepository<UserReportPhoto, Long> {

    List<UserReportPhoto> findAll();
}
