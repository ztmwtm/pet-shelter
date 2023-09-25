/*package com.example.petshelter.service;

import com.example.petshelter.entity.UserReportPhoto;
import com.example.petshelter.repository.UserReportPhotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserReportPhotoServiceTest {

    @InjectMocks
    private UserReportPhotoService userReportPhotoService;

    @Mock
    private UserReportPhotoRepository userReportPhotoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUserReportPhoto() {
        File file = new File("test.jpg");

        // Mock the save method of the repository
        doNothing().when(userReportPhotoRepository).save(any(String.class));

        // Call the method to be tested
        userReportPhotoService.createUserReportPhoto(file);

        // Verify that the save method of the repository was called with the file path
        verify(userReportPhotoRepository, times(1)).save("test.jpg");
    }

    @Test
    public void testUpdateUserReportPhoto() {
        String fileId = "12345";

        // Mock the save method of the repository
        doNothing().when(userReportPhotoRepository).save(fileId);

        // Call the method to be tested
        userReportPhotoService.updateUserReportPhoto(fileId);

        // Verify that the save method of the repository was called with the fileId
        verify(userReportPhotoRepository, times(1)).save(fileId);
    }

    @Test
    public void testGetUserReportPhoto() {
        Long id = 1L;
        UserReportPhoto expectedPhoto = new UserReportPhoto(); // Replace with your actual UserReportPhoto object

        // Mock the findById method of the repository to return the expected photo
        when(userReportPhotoRepository.findById(id)).thenReturn(Optional.of(expectedPhoto));

        // Call the method to be tested
        Optional<UserReportPhoto> result = userReportPhotoService.getUserReportPhoto(id);

        // Verify that the result matches the expected photo
        assertEquals(Optional.of(expectedPhoto), result);
    }

    @Test
    public void testDeleteUserReportPhoto() {
        Long id = 1L;

        // Mock the deleteById method of the repository
        doNothing().when(userReportPhotoRepository).deleteById(id);

        // Call the method to be tested
        userReportPhotoService.deleteUserReportPhoto(id);

        // Verify that the deleteById method of the repository was called with the correct id
        verify(userReportPhotoRepository, times(1)).deleteById(id);
    }
}

 */