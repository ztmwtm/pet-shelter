package com.example.petshelter.repository;

import com.example.petshelter.entity.UserReportPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReportPhotoRepository extends JpaRepository<UserReportPhoto, Long> {

    Optional<UserReportPhoto> getUserReportPhotoByUserReport_id(Long id);

}
