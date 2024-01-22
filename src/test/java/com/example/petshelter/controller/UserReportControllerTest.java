package com.example.petshelter.controller;

import com.example.petshelter.entity.UserReport;
import com.example.petshelter.repository.UserReportRepository;
import com.example.petshelter.service.UserReportPhotoService;
import com.example.petshelter.service.UserReportService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
    @MockBean
    private UserReportPhotoService userReportPhotoService;
    @SpyBean
    private UserReportService userReportService;
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
    void getUserReportByIdTest() throws Exception {
        UserReport userReport = generate(1L);

        when(userReportRepository.findById(1L)).thenReturn(Optional.of(userReport));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/1")
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
        verify(userReportRepository, times(1)).findById(1L);
    }

    @Test
    void getAllUserReportsTest() throws Exception {
        List<UserReport> userReports = Stream.iterate(1L, id -> id + 1)
                .map(this::generate)
                .limit(20)
                .toList();

        List<UserReport> expectedResult = userReports.stream()
                .toList();

        when(userReportRepository.findAll()).thenReturn(userReports);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<UserReport> userReports1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );
                    assertThat(userReports1).isNotNull().isNotEmpty();
                    Stream.iterate(0, index -> index + 1)
                            .limit(userReports1.size())
                            .forEach(index -> {
                                UserReport userReport1 = userReports1.get(index);
                                UserReport expected = expectedResult.get(index);
                                assertThat(userReport1).isNotNull();
                                assertThat(userReport1.getId()).isEqualTo(expected.getId());
                                assertThat(userReport1.getHealth()).isEqualTo(expected.getHealth());
                                assertThat(userReport1.getPetDiet()).isEqualTo(expected.getPetDiet());
                                assertThat(userReport1.getBehavior()).isEqualTo(expected.getBehavior());
                            });
                });
        verify(userReportRepository, times(1)).findAll();
    }

    @Test
    void updateUserReportTest() throws Exception {
        UserReport userReport = generate(1L);

        UserReport oldUserReport = generate(1L);

        when(userReportRepository.findById(1L)).thenReturn(Optional.of(oldUserReport));

        oldUserReport.setHealth(userReport.getHealth());
        oldUserReport.setBehavior(userReport.getBehavior());
        oldUserReport.setPetDiet(userReport.getPetDiet());

        when(userReportRepository.save(any(UserReport.class))).thenReturn(oldUserReport);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/report/1")
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
    void deleteUserReportByIdTest() throws Exception {
        UserReport userReport = generate(1L);

        when(userReportRepository.findById(1L)).thenReturn(Optional.of(userReport));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/report/1")
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
        verify(userReportRepository, times(1)).deleteById(1L);
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