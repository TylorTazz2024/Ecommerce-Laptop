package za.ac.cput.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.util.Helper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    void setUp() {
        // Build a test user
        user = UserFactory.createUser(
                "Bradly",
                "Jackson",
                "password123",
                Role.CUSTOMER,
                "bjackson@gmail.com",
                "0649484181"
        );
        user = new User.Builder()
                .copy(user)
                .setUserID(Helper.generateId()) // ensure unique ID
                .build();
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Bradly"))
                .andExpect(jsonPath("$.lastName").value("Jackson"));
    }

    @Test
    void read() throws Exception {
        mockMvc.perform(get("/api/users/read/{id}", user.getUserID()))
                .andExpect(status().isOk());
    }

    @Test
    void update() throws Exception {
        User updatedUser = new User.Builder()
                .copy(user)
                .setFirstName("Brad")
                .build();

        mockMvc.perform(put("/api/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Brad"));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(delete("/api/users/delete/{id}", user.getUserID()))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByEmail() throws Exception {
        mockMvc.perform(get("/api/users/email/{email}", "bjackson@gmail.com"))
                .andExpect(status().isOk());
    }

    @Test
    void findByRole() throws Exception {
        mockMvc.perform(get("/api/users/role/{role}", "CUSTOMER"))
                .andExpect(status().isOk());
    }
}
