package com.example.petshelter.controller;

import com.example.petshelter.entity.User;
import com.example.petshelter.repository.UserRepository;
import com.example.petshelter.service.UserService;
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

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void addUserTest() throws Exception {
        User user = generate(1L);

        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    User user1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            User.class
                    );
                    assertThat(user1).isNotNull();
                    assertThat(user1.getId()).isEqualTo(1L);
                    assertThat(user1.getFirstName()).isEqualTo(user.getFirstName());
                    assertThat(user1.getTgUsername()).isEqualTo(user.getTgUsername());
                    assertThat(user1.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
                    assertThat(user1.getChatId()).isEqualTo(user.getChatId());
                });
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserByIdTest() throws Exception {
        User user = generate(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    User user1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            User.class
                    );
                    assertThat(user1).isNotNull();
                    assertThat(user1.getId()).isEqualTo(1L);
                    assertThat(user1.getFirstName()).isEqualTo(user.getFirstName());
                    assertThat(user1.getTgUsername()).isEqualTo(user.getTgUsername());
                    assertThat(user1.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
                    assertThat(user1.getChatId()).isEqualTo(user.getChatId());
                });
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getAllUsersTest() throws Exception {
        List<User> users = Stream.iterate(1L, id -> id + 1)
                .map(this::generate)
                .limit(20)
                .toList();

        List<User> expectedResult = users.stream()
                .toList();

        when(userRepository.findAll()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<User> users1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );
                    assertThat(users1).isNotNull().isNotEmpty();
                    Stream.iterate(0, index -> index + 1)
                            .limit(users1.size())
                            .forEach(index -> {
                                User user1 = users1.get(index);
                                User expected = expectedResult.get(index);
                                assertThat(user1).isNotNull();
                                assertThat(user1.getId()).isEqualTo(expected.getId());
                                assertThat(user1.getFirstName()).isEqualTo(expected.getFirstName());
                                assertThat(user1.getTgUsername()).isEqualTo(expected.getTgUsername());
                                assertThat(user1.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
                                assertThat(user1.getChatId()).isEqualTo(expected.getChatId());
                            });
                });
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void updateUserTest() throws Exception {
        User user = generate(1L);

        User oldUser = generate(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(oldUser));

        oldUser.setChatId(user.getChatId());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setTgUsername(user.getTgUsername());
        oldUser.setPhoneNumber(user.getPhoneNumber());

        when(userRepository.save(any(User.class))).thenReturn(oldUser);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/user/1")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    User user1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            User.class
                    );
                    assertThat(user1).isNotNull();
                    assertThat(user1.getId()).isEqualTo(1L);
                    assertThat(user1.getFirstName()).isEqualTo(user.getFirstName());
                    assertThat(user1.getTgUsername()).isEqualTo(user.getTgUsername());
                    assertThat(user1.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
                    assertThat(user1.getChatId()).isEqualTo(user.getChatId());

                });
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUserByIdTest() throws Exception {
        User user = generate(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    User user1 = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            User.class
                    );
                    assertThat(user1).isNotNull();
                    assertThat(user1.getId()).isEqualTo(1L);
                    assertThat(user1.getFirstName()).isEqualTo(user.getFirstName());
                    assertThat(user1.getTgUsername()).isEqualTo(user.getTgUsername());
                    assertThat(user1.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
                    assertThat(user1.getChatId()).isEqualTo(user.getChatId());
                });
        verify(userRepository, times(1)).deleteById(1L);
    }

    private User generate(Long id) {
        User user = new User();
        user.setId(id);
        user.setChatId(1000L);
        user.setFirstName("First Name");
        user.setTgUsername("TG Name");
        user.setPhoneNumber("+7-904-585-96-89");
        return user;
    }
}