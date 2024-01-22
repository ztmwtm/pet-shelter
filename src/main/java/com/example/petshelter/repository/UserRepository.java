package com.example.petshelter.repository;

import com.example.petshelter.entity.User;
import com.example.petshelter.type.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByChatId(final Long chatId);

    List<User> findUsersByRoleIs(final UserRole role);

    @Query(value = """
            SELECT users.id
            FROM users
                     LEFT JOIN pets p on users.id = p.user_id
            WHERE users.role = 'ADOPTER'
              AND p.pet_status = 'ADOPTED'
              AND users.id NOT IN
                  (SELECT users.id
                   FROM users
                            LEFT JOIN user_reports ur on users.id = ur.user_id
                   WHERE ur.date_of_creation = CURRENT_DATE
                   GROUP BY users.id)
            """,
            nativeQuery = true)
    List<Long> getUsersWithFailedReport();

    @Query(value = """
            SELECT users.id
            FROM users
                     LEFT JOIN pets p on users.id = p.user_id
            WHERE users.role = 'ADOPTER'
              AND p.pet_status = 'ADOPTED'
              AND users.id IN
                  (SELECT users.id
                   FROM users
                            LEFT JOIN user_reports ur on users.id = ur.user_id
                   GROUP BY users.id
                   HAVING MAX(ur.date_of_creation) <= (CURRENT_DATE - :days)
                   OR MAX(ur.date_of_creation) IS NULL)
            """,
            nativeQuery = true)
    List<Long> getUsersWithMissedReport(@Param("days") int daysReportMissedToVolunteerAlarm);
}
