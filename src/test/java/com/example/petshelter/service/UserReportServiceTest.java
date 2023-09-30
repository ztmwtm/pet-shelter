package com.example.petshelter.service;

import com.example.petshelter.entity.UserReport;
import com.example.petshelter.repository.UserReportRepository;
import com.example.petshelter.util.UserReportStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserReportServiceTest {

    private UserReportRepository userReportRepository;
    private UserReportService userReportService;
    UserReport userReport = new UserReport();
    @BeforeEach
    public void beforeEach() {
        userReportRepository = mock(UserReportRepository.class);
        userReportService = new UserReportService(userReportRepository);
    }

    @Test
    public void testAddUserReport() {

        when(userReportRepository.save(any())).thenReturn(userReport);
        UserReport actual = userReportService.addUserReport(userReport);
        UserReport expected = userReport;

        assertEquals(expected, actual);
        verify(userReportRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateUserReport() {
        UserReport userReport = new UserReport();
        userReport.setId(1L);

        when(userReportRepository.save(any())).thenReturn(userReport);
        when(userReportRepository.findById(1L)).thenReturn(Optional.of(userReport));
        UserReport actual = userReportService.updateUserReport(1L, userReport);
        UserReport expected = userReport;

        assertEquals(expected, actual);
        verify(userReportRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateUserReportRuntimeException() {
        when(userReportRepository.findById(any())).thenReturn(null);
        assertThrows(RuntimeException.class,
                () -> userReportService.updateUserReport(1L,userReport)
        );
    }

    @Test
    public void testGetUserReportById() {
        UserReport userReport = new UserReport();
        userReport.setId(1L);

        when(userReportRepository.findById(1L)).thenReturn(Optional.of(userReport));
        UserReport actual = userReportService.getUserReportById(1L);
        UserReport expected = userReport;

        assertEquals(expected, actual);
        verify(userReportRepository, times(1)).findById(1L);
    }

    @Test
    public void testUserReportByIdRuntimeException() {
        when(userReportRepository.findById(any())).thenReturn(null);
        assertThrows(RuntimeException.class,
                () -> userReportService.getUserReportById(1L)
        );
    }
    @Test
    public void testGetAllUserReports() {
        List<UserReport> userReports = List.of(new UserReport());

        when(userReportRepository.findAll()).thenReturn(userReports);
        Collection<UserReport> actual = userReportService.getAllUserReports();
        List<UserReport> expected = userReports;

        assertEquals(expected, actual);
        verify(userReportRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteUserReportById() {
        Long userId = 1L;
        UserReport userReport = new UserReport();
        userReport.setId(userId);

        when(userReportRepository.findById(userId)).thenReturn(Optional.of(userReport));
        UserReport actual = userReportService.deleteUserReportById(userId);
        UserReport expected = userReport;

        assertEquals(expected, actual);
        verify(userReportRepository, times(1)).findById(userId);
        verify(userReportRepository, times(1)).deleteById(userId);
    }
    @Test
    public void testDeleteUserReportByIdRuntimeException() {
        when(userReportRepository.findById(any())).thenReturn(null);
        assertThrows(RuntimeException.class,
                () -> userReportService.deleteUserReportById(1L)
        );
    }

    @Test
    public void testGetUserReportByUserIdAndStatusCreated() {
        UserReport userReport = new UserReport();
        userReport.setId(1L);

        when(userReportRepository.getUserReportByUser_idAndStatusCreated(1L)).thenReturn(Optional.of(userReport));
        UserReport actual = userReportService.getUserReportByUserIdAndStatusCreated(1L);
        UserReport expected = userReport;

        assertEquals(expected, actual);
        verify(userReportRepository, times(1)).getUserReportByUser_idAndStatusCreated(1L);
    }

    @Test
    public void testGetUserReportByStatus() {
        List<UserReport> userReports = List.of(new UserReport());
        UserReportStatus status = UserReportStatus.CREATED;

        when(userReportRepository.getUserReportByStatus(status)).thenReturn(userReports);
        List<UserReport> actual = userReportService.getUserReportByStatus(status);
        List<UserReport> expected = userReports;

        assertEquals(expected, actual);
        verify(userReportRepository, times(1)).getUserReportByStatus(status);
    }

}
