package com.example.petshelter.controller;

import com.example.petshelter.entity.Shelter;
import com.example.petshelter.repository.ShelterRepository;
import com.example.petshelter.service.ShelterService;
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

@WebMvcTest(controllers = ShelterController.class)
class ShelterControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private ShelterService shelterService;
    @MockBean
    private ShelterRepository shelterRepository;

    @Test
    void addShelterTest() throws Exception {
        Shelter shelter = generate(1L);

        when(shelterRepository.save(any(Shelter.class))).thenReturn(shelter);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/shelter")
                        .content(objectMapper.writeValueAsString(shelter))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Shelter shelter1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            Shelter.class
                    );
                    assertThat(shelter1).isNotNull();
                    assertThat(shelter1.getId()).isEqualTo(1L);
                    assertThat(shelter1.getName()).isEqualTo(shelter.getName());
                    assertThat(shelter1.getPetType()).isEqualTo(shelter.getPetType());
                    assertThat(shelter1.getPhoneNumber()).isEqualTo(shelter.getPhoneNumber());
                });
        verify(shelterRepository, times(1)).save(any(Shelter.class));
    }

    @Test
    void getShelterByIdTest() throws Exception {
        Shelter shelter = generate(1L);

        when(shelterRepository.findById(1L)).thenReturn(Optional.of(shelter));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/shelter/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Shelter shelter1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            Shelter.class
                    );
                    assertThat(shelter1).isNotNull();
                    assertThat(shelter1.getId()).isEqualTo(1L);
                    assertThat(shelter1.getName()).isEqualTo(shelter.getName());
                    assertThat(shelter1.getPetType()).isEqualTo(shelter.getPetType());
                    assertThat(shelter1.getPhoneNumber()).isEqualTo(shelter.getPhoneNumber());
                });
        verify(shelterRepository, times(1)).findById(1L);
    }

    @Test
    void getAllSheltersTest() throws Exception {
        List<Shelter> shelters = Stream.iterate(1L, id -> id + 1)
                .map(this::generate)
                .limit(10)
                .toList();

        List<Shelter> expectedResult = shelters.stream()
                .toList();

        when(shelterRepository.findAll()).thenReturn(shelters);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/shelter/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<Shelter> shelters1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );
                    assertThat(shelters1).isNotNull().isNotEmpty();
                    Stream.iterate(0, index -> index + 1)
                            .limit(shelters1.size())
                            .forEach(index -> {
                                Shelter shelter1 = shelters1.get(index);
                                Shelter expected = expectedResult.get(index);
                                assertThat(shelter1).isNotNull();
                                assertThat(shelter1.getId()).isEqualTo(expected.getId());
                                assertThat(shelter1.getName()).isEqualTo(expected.getName());
                                assertThat(shelter1.getPetType()).isEqualTo(expected.getPetType());
                                assertThat(shelter1.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
                            });
                });
        verify(shelterRepository, times(1)).findAll();
    }

    @Test
    void updateShelterTest() throws Exception {
        Shelter shelter = generate(1L);

        Shelter oldShelter = generate(1L);

        when(shelterRepository.findById(1L)).thenReturn(Optional.of(oldShelter));

        oldShelter.setName(shelter.getName());
        oldShelter.setAddress(shelter.getAddress());
        oldShelter.setPhoneNumber(shelter.getPhoneNumber());
        oldShelter.setPetType(shelter.getPetType());

        when(shelterRepository.save(any(Shelter.class))).thenReturn(oldShelter);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/shelter/1")
                        .content(objectMapper.writeValueAsString(shelter))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Shelter shelter1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            Shelter.class
                    );
                    assertThat(shelter1).isNotNull();
                    assertThat(shelter1.getId()).isEqualTo(1L);
                    assertThat(shelter1.getName()).isEqualTo(shelter.getName());
                    assertThat(shelter1.getAddress()).isEqualTo(shelter.getAddress());
                    assertThat(shelter1.getPetType()).isEqualTo(shelter.getPetType());
                    assertThat(shelter1.getPhoneNumber()).isEqualTo(shelter.getPhoneNumber());

                });
        verify(shelterRepository, times(1)).save(any(Shelter.class));
    }

    @Test
    void deleteShelterByIdTest() throws Exception {
        Shelter shelter = generate(1L);

        when(shelterRepository.findById(1L)).thenReturn(Optional.of(shelter));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/shelter/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Shelter shelter1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            Shelter.class
                    );
                    assertThat(shelter1).isNotNull();
                    assertThat(shelter1.getId()).isEqualTo(1L);
                    assertThat(shelter1.getName()).isEqualTo(shelter.getName());
                    assertThat(shelter1.getAddress()).isEqualTo(shelter.getAddress());
                    assertThat(shelter1.getPetType()).isEqualTo(shelter.getPetType());
                    assertThat(shelter1.getPhoneNumber()).isEqualTo(shelter.getPhoneNumber());
                });
        verify(shelterRepository, times(1)).deleteById(1L);
    }

    private Shelter generate(Long id) {
        Shelter shelter = new Shelter();
        shelter.setId(id);
        shelter.setName("ShelterName");
        shelter.setAddress("Address");
        shelter.setPhoneNumber("+7-495-963-78-96");
        shelter.setPetType(PetType.CAT);
        return shelter;
    }
}