package com.example.petshelter.service1;

import com.example.petshelter.entity.Shelter;
import com.example.petshelter.repository.ShelterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShelterServiceTest {

    @InjectMocks
    private ShelterService shelterService;

    @Mock
    private ShelterRepository shelterRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddShelter() {
        Shelter shelterToAdd = new Shelter(/* Initialize a Shelter object here */);
        when(shelterRepository.save(shelterToAdd)).thenReturn(shelterToAdd);

        Shelter addedShelter = shelterService.addShelter(shelterToAdd);

        assertEquals(shelterToAdd, addedShelter);
        verify(shelterRepository, times(1)).save(shelterToAdd);
    }

    @Test
    public void testUpdateShelter() {
        Long idToUpdate = 1L;
        Shelter updatedShelter = new Shelter(/* Initialize a Shelter object here */);
        Shelter existingShelter = new Shelter(/* Initialize an existing Shelter object here */);

        when(shelterRepository.findById(idToUpdate)).thenReturn(Optional.of(existingShelter));
        when(shelterRepository.save(existingShelter)).thenReturn(updatedShelter);

        Shelter result = shelterService.updateShelter(idToUpdate, updatedShelter);

        assertEquals(updatedShelter, result);
        verify(shelterRepository, times(1)).findById(idToUpdate);
        verify(shelterRepository, times(1)).save(existingShelter);
    }

    @Test
    public void testGetShelterById() {
        Long idToGet = 1L;
        Shelter shelterToReturn = new Shelter(/* Initialize a Shelter object here */);

        when(shelterRepository.findById(idToGet)).thenReturn(Optional.of(shelterToReturn));

        Shelter result = shelterService.getShelterById(idToGet);

        assertEquals(shelterToReturn, result);
        verify(shelterRepository, times(1)).findById(idToGet);
    }

    @Test
    public void testGetShelterByName() {
        String nameToGet = "ShelterName";
        Shelter shelterToReturn = new Shelter(/* Initialize a Shelter object here */);

        when(shelterRepository.findShelterByName(nameToGet)).thenReturn(shelterToReturn);

        Shelter result = shelterService.getShelterByName(nameToGet);

        assertEquals(shelterToReturn, result);
        verify(shelterRepository, times(1)).findShelterByName(nameToGet);
    }

    @Test
    public void testGetAllShelters() {
        List<Shelter> sheltersToReturn = new ArrayList<>();
        // Add some Shelter objects to the sheltersToReturn list

        when(shelterRepository.findAll()).thenReturn(sheltersToReturn);

        Collection<Shelter> result = shelterService.getAllShelters();

        assertEquals(sheltersToReturn, result);
        verify(shelterRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteShelterById() {
        Long idToDelete = 1L;
        Shelter shelterToDelete = new Shelter(/* Initialize a Shelter object here */);

        when(shelterRepository.findById(idToDelete)).thenReturn(Optional.of(shelterToDelete));

        Shelter result = shelterService.deleteShelterById(idToDelete);

        assertEquals(shelterToDelete, result);
        verify(shelterRepository, times(1)).findById(idToDelete);
        verify(shelterRepository, times(1)).deleteById(idToDelete);
    }
}
