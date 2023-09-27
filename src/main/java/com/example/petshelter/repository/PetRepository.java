package com.example.petshelter.repository;

import com.example.petshelter.entity.Pet;
import com.example.petshelter.type.PetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> getPetByAdopter_Id(Long userId);

    List<Pet> getPetsByAdopter_Id(Long userId);

    List<Pet> findPetsByPetStatus(final PetStatus status);

    @Query(value = """
            SELECT pets.id
            FROM pets
                     LEFT JOIN user_reports on pets.id = user_reports.pet_id
            WHERE pet_status = 'ADOPTED'
              AND days_to_adaptation <= (SELECT COUNT(user_reports.id)
                                        FROM user_reports
                                         WHERE status = 'VERIFIED'
                                           AND user_reports.user_id = pets.user_id
                                        GROUP BY user_id)
            GROUP BY pets.id
                        """
            , nativeQuery = true)
    List<Long> getPetsIdReadyToKept();

    List<Pet> findPetByPetStatus(PetStatus status);
}
