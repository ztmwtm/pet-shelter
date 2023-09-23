package com.example.petshelter.service;

import com.example.petshelter.entity.Pet;
import com.example.petshelter.repository.PetRepository;
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

public class PetServiceTest {

    @InjectMocks
    private PetService petService;

    @Mock
    private PetRepository petRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddPet() {
        Pet petToAdd = new Pet(/* Initialize a Pet object here */);
        when(petRepository.save(petToAdd)).thenReturn(petToAdd);

        Pet addedPet = petService.addPet(petToAdd);

        assertEquals(petToAdd, addedPet);
        verify(petRepository, times(1)).save(petToAdd);
    }

    @Test
    public void testUpdatePet() {
        Long idToUpdate = 1L;
        Pet updatedPet = new Pet(/* Initialize a Pet object here */);
        Pet existingPet = new Pet(/* Initialize an existing Pet object here */);

        when(petRepository.findById(idToUpdate)).thenReturn(Optional.of(existingPet));
        when(petRepository.save(existingPet)).thenReturn(updatedPet);

        Pet result = petService.updatePet(idToUpdate, updatedPet);

        assertEquals(updatedPet, result);
        verify(petRepository, times(1)).findById(idToUpdate);
        verify(petRepository, times(1)).save(existingPet);
    }

    @Test
    public void testGetPetById() {
        Long idToGet = 1L;
        Pet petToReturn = new Pet(/* Initialize a Pet object here */);

        when(petRepository.findById(idToGet)).thenReturn(Optional.of(petToReturn));

        Pet result = petService.getPetById(idToGet);

        assertEquals(petToReturn, result);
        verify(petRepository, times(1)).findById(idToGet);
    }

    @Test
    public void testGetAllPets() {
        List<Pet> petsToReturn = new ArrayList<>();
        // Add some Pet objects to the petsToReturn list

        when(petRepository.findAll()).thenReturn(petsToReturn);

        Collection<Pet> result = petService.getAllPets();

        assertEquals(petsToReturn, result);
        verify(petRepository, times(1)).findAll();
    }

    @Test
    public void testDeletePetById() {
        Long idToDelete = 1L;
        Pet petToDelete = new Pet(/* Initialize a Pet object here */);

        when(petRepository.findById(idToDelete)).thenReturn(Optional.of(petToDelete));

        Pet result = petService.deletePetById(idToDelete);

        assertEquals(petToDelete, result);
        verify(petRepository, times(1)).findById(idToDelete);
        verify(petRepository, times(1)).deleteById(idToDelete);
    }
}
