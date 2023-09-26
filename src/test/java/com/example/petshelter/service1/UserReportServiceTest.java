package com.example.petshelter.service1;

import com.example.petshelter.entity.UserReport;
import com.example.petshelter.repository.UserReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserReportServiceTest {

    @Mock
    private UserReportRepository userReportRepository;

    @InjectMocks
    private UserReportService userReportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUserReport() {
        // Создание моков и данных для тестирования
        String text = "Текст отчета";
        UserReport userReport = new UserReport();
        when(userReportRepository.save((UserReport) any())).thenReturn(userReport);

        // Вызов метода, который мы тестируем
        userReportService.createUserReport(text);

        // Проверка, что метод save был вызван с правильным аргументом
        verify(userReportRepository).save(text);
    }

    @Test
    public void testUpdateUserReport() {
        // Создание моков и данных для тестирования
        String fileId = "someFileId";
        UserReport userReport = new UserReport();
        when(userReportRepository.save((UserReport) any())).thenReturn(userReport);

        // Вызов метода, который мы тестируем
        userReportService.updateUserReport(fileId);

        // Проверка, что метод save был вызван с правильным аргументом
        verify(userReportRepository).save(fileId);
    }

    @Test
    public void testGetUserReport() {
        // Создание моков и данных для тестирования
        Long id = 1L;
        UserReport userReport = new UserReport();
        when(userReportRepository.findById(id)).thenReturn(Optional.of(userReport));

        // Вызов метода, который мы тестируем
        Optional<UserReport> result = userReportService.getUserReport(id);

        // Проверка, что результат содержит ожидаемый отчет
        assertTrue(result.isPresent());
        assertEquals(userReport, result.get());
    }

    @Test
    public void testDeleteUserReport() {
        // Создание моков и данных для тестирования
        Long id = 1L;

        // Вызов метода, который мы тестируем
        userReportService.deleteUserReport(id);

        // Проверка, что метод deleteById был вызван с правильным аргументом
        verify(userReportRepository).deleteById(id);
    }
}
