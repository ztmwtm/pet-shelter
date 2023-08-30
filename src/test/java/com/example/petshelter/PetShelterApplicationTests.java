package com.example.petshelter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


@TestPropertySource(properties = {"spring.datasource.url=${PET_SHELTER_MYSQL_DB_URL}",
        "spring.datasource.username=${PET_SHELTER_MYSQL_DB_USERNAME}",
        "spring.datasource.password=${PET_SHELTER_MYSQL_DB_PASSWORD}"})
@SpringBootTest
class PetShelterApplicationTests {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;

    @Test
    void contextLoadsTest() {
    }

    @Test
    void dbConnectionTest() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, password);
        assertTrue(conn.isValid(10), """
                        
                        Connection to DB failed
                        ⠀⠀ ⣀⣤⣶⠶⠿⠿⠿⠿⠿⠶⣶⣦⣄⡀⠀⠀⠀⠀
                        ⢀⣾⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀   ⠈⠙⢿⡆⠀⠀⠀
                        ⢸⣿⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀   ⣠⣾⡇⠀⠀⠀
                        ⢸⣿⠉⠛⠿⠶⣶⣶⣶⣶⣶⡶⠾⠟⠋⢹⡇⠀⠀⠀
                        ⢸⣿⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀   ⠠⠾⠇⠀⠀⠀
                        ⢸⣿⠉⠛⠿⠶⣶⣶⣶⣶⠖⠀ ⣠⣤⣦⣦⣤⡀⠀⠀
                        ⢸⣿⣦⡀⠀⠀⠀⠀⠀⠀   ⢠⣾⣿⣿⠀⢹⣿⣿⣦⠀
                        ⢸⣿⠉⠛⠿⠶⣶⣶⡆⠀  ⣿⣿⠿⢿⠀⢸⠿⢿⣿⡆
                        ⠈⢿⣦⡀⠀⠀⠀⠀⠀⠀   ⠹⣿⣦⡀⠀⠀⣠⣾⡿⠁
                        ⠀⠀⠉⠛⠿⠶⣶⣶⣶⣦⡀  ⠙⠿⢿⣶⣾⠿⠛⠁⠀
                        """);
    }

}
