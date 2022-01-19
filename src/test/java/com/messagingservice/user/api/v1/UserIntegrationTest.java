package com.messagingservice.user.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messagingservice.user.model.CreateUserRequest;
import com.messagingservice.user.persistence.entity.User;
import com.messagingservice.user.persistence.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    private void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    public void createUserReturnsCreated() throws Exception {
        var createUserRequest = new CreateUserRequest("user 1");

        mockMvc.perform(post("/v1/messages/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nickname").value(createUserRequest.getNickname()))
                .andExpect(jsonPath("$.created_at").isNotEmpty());
    }

    @Test
    public void createUserReturnsBadRequest() throws Exception {
        var user = new User();
        user.setNickname("user 1");

        userRepository.save(user);

        var createUserRequest = new CreateUserRequest("user 1");

        mockMvc.perform(post("/v1/messages/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value("400 BAD_REQUEST \"User with nickname user 1 already exist\""));
    }

}