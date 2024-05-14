package com.ironhack.greatreads.controller.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.greatreads.model.user.Role;
import com.ironhack.greatreads.model.user.User;
import com.ironhack.greatreads.repository.user.UserRepository;
import com.ironhack.greatreads.service.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        user1 = new User("Matilda Wormwood", "matilda", "mwormwood@example.com", "brucebruce");
        user2 = new User("Catherine Morland", "kattym", "catherine.morland@example.com", "udolpho123");


        userService.saveRole(new Role(null, "ROLE_USER"));
        userService.saveRole(new Role(null, "ROLE_LIBRARIAN"));
        userService.saveRole(new Role(null, "ROLE_ADMIN"));
        userService.createUser(user1, "ROLE_USER");
        userService.createUser(user2, "ROLE_USER");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void getUsersTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/users").with(user("test").roles("ADMIN")))
                .andExpect(status().isOk())
                .andReturn();

        List<User> users = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<User>>() {});
        assertEquals(2, users.size());
        assertEquals(user1.getUsername(), users.get(0).getUsername());
        assertEquals(user2.getUsername(), users.get(1).getUsername());
    }

    @Test
    void addUserTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/users")
                        .content("{\"name\": \"Elizabeth Bennet\", \"username\": \"lizzyreads\", \"email\": \"e.bennet@example.com\", \"password\": \"prideandprejudice\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        User user = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertEquals("Elizabeth Bennet", user.getName());
        assertEquals("lizzyreads", user.getUsername());
    }

    @Test
    void promoteUserToLibrarianTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/users/{id}/promote", user1.getId()).with(user("test").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"ROLE_LIBRARIAN\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        User promotedUser = userService.getUserById(user1.getId());
        Collection<Role> roles = promotedUser.getRoles();

        assertTrue(roles.stream().anyMatch(role -> role != null && role.getName().equals("ROLE_LIBRARIAN")));
    }

    @Test
    void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/users/{id}", user1.getId()).with(user("test").roles("ADMIN")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/users/{id}", user1.getId()).with(user("test").roles("ADMIN")))
                .andExpect(status().isNotFound());
    }
}
