package com.example.petshelter.controller;

import com.example.petshelter.entity.UserReport;
import com.example.petshelter.repository.UserReportRepository;
import com.example.petshelter.service.TelegramBotService;
import com.example.petshelter.service.UserReportPhotoService;
import com.example.petshelter.service.UserReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserReportController.class)
class UserReportControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private UserReportService userReportService;
    @SpyBean
    private UserReportPhotoService userReportPhotoService;
    @SpyBean
    private TelegramBot telegramBot;
    @SpyBean
    private TelegramBotService telegramBotService;
    @MockBean
    private UserReportRepository userReportRepository;

    @Test
    void addUserReportTest() throws Exception {
        UserReport userReport = generate(1L);

        when(userReportRepository.save(any(UserReport.class))).thenReturn(userReport);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/report")
                        .content(objectMapper.writeValueAsString(userReport))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    UserReport userReport1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            UserReport.class
                    );
                    assertThat(userReport1).isNotNull();
                    assertThat(userReport1.getId()).isEqualTo(1L);
                    assertThat(userReport1.getHealth()).isEqualTo(userReport.getHealth());
                    assertThat(userReport1.getPetDiet()).isEqualTo(userReport.getPetDiet());
                    assertThat(userReport1.getBehavior()).isEqualTo(userReport.getBehavior());
                });
        verify(userReportRepository, times(1)).save(any(UserReport.class));
    }

    @Test
    void getUserReportById() {
    }

    @Test
    void getAllUserReports() {
    }

    @Test
    void updateUserReport() {
    }

    @Test
    void deleteUserReportById() {
    }

    private UserReport generate(Long id) {
        UserReport userReport = new UserReport();
        userReport.setId(id);
        userReport.setBehavior("very bad");
        userReport.setPetDiet("meat and fish");
        userReport.setHealth("strong");
        return userReport;
    }
}