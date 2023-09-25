package com.example.petshelter.controller;

import com.example.petshelter.entity.Pet;
import com.example.petshelter.repository.PetRepository;
import com.example.petshelter.service.PetService;
import com.example.petshelter.type.PetType;
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

@WebMvcTest(controllers = PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private PetService petService;
    @MockBean
    private PetRepository petRepository;

    @Test
    void addPetTest() throws Exception {
        Pet pet = generate(1L);

        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/pet")
                        .content(objectMapper.writeValueAsString(pet))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Pet pet1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            Pet.class
                    );
                    assertThat(pet1).isNotNull();
                    assertThat(pet1.getId()).isEqualTo(1L);
                    assertThat(pet1.getNickname()).isEqualTo(pet.getNickname());
                    assertThat(pet1.getPetType()).isEqualTo(pet.getPetType());
                    assertThat(pet1.getSpecies()).isEqualTo(pet.getSpecies());
                });
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    void getPetByIdTest() throws Exception {
        Pet pet = generate(1L);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pet/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Pet pet1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            Pet.class
                    );
                    assertThat(pet1).isNotNull();
                    assertThat(pet1.getId()).isEqualTo(1L);
                    assertThat(pet1.getNickname()).isEqualTo(pet.getNickname());
                    assertThat(pet1.getPetType()).isEqualTo(pet.getPetType());
                    assertThat(pet1.getSpecies()).isEqualTo(pet.getSpecies());
                });
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    void getAllPetsTest() throws Exception {
        List<Pet> pets = Stream.iterate(1L, id -> id + 1)
                .map(this::generate)
                .limit(20)
                .toList();

        List<Pet> expectedResult = pets.stream()
                .toList();

        when(petRepository.findAll()).thenReturn(pets);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pet/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<Pet> pets1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );
                    assertThat(pets1).isNotNull().isNotEmpty();
                    Stream.iterate(0, index -> index + 1)
                            .limit(pets1.size())
                            .forEach(index -> {
                                Pet pet1 = pets1.get(index);
                                Pet expected = expectedResult.get(index);
                                assertThat(pet1.getId()).isEqualTo(expected.getId());
                                assertThat(pet1.getNickname()).isEqualTo(expected.getNickname());
                                assertThat(pet1.getSpecies()).isEqualTo(expected.getSpecies());
                                assertThat(pet1.getPetType()).isEqualTo(expected.getPetType());
                            });
                });
        verify(petRepository, times(1)).findAll();
    }

    @Test
    void updatePetTest() throws Exception {
        Pet pet = generate(1L);

        Pet oldPet = generate(1L);

        when(petRepository.findById(1L)).thenReturn(Optional.of(oldPet));

        oldPet.setNickname(pet.getNickname());
        oldPet.setSpecies(pet.getSpecies());

        when(petRepository.save(any(Pet.class))).thenReturn(oldPet);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/pet/1")
                        .content(objectMapper.writeValueAsString(pet))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Pet pet1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            Pet.class
                    );
                    assertThat(pet1).isNotNull();
                    assertThat(pet1.getId()).isEqualTo(1L);
                    assertThat(pet1.getNickname()).isEqualTo(pet.getNickname());
                    assertThat(pet1.getPetType()).isEqualTo(pet.getPetType());
                    assertThat(pet1.getSpecies()).isEqualTo(pet.getSpecies());
                });
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    void deletePetByIdTest() throws Exception {
        Pet pet = generate(1L);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/pet/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Pet pet1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            Pet.class
                    );
                    assertThat(pet1).isNotNull();
                    assertThat(pet1.getId()).isEqualTo(1L);
                    assertThat(pet1.getNickname()).isEqualTo(pet.getNickname());
                    assertThat(pet1.getPetType()).isEqualTo(pet.getPetType());
                    assertThat(pet1.getSpecies()).isEqualTo(pet.getSpecies());
                });
        verify(petRepository, times(1)).deleteById(1L);
    }

    private Pet generate(Long id) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setSpecies("siam");
        pet.setNickname("Barsik");
        pet.setPetType(PetType.CAT);
        return pet;
    }
}