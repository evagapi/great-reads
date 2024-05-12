package com.ironhack.greatreads.controller.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.greatreads.model.people.Author;
import com.ironhack.greatreads.repository.people.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AuthorControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AuthorRepository authorRepository;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        author1 = new Author("Brandon", "Sanderson");
        author2 = new Author("Roal", "Dahl");
        authorRepository.saveAll(List.of(author1, author2));
    }

    @AfterEach
    void tearDown() {
        authorRepository.deleteAll();
    }

    @Test
    void getAllAuthorsTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Author> authors = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<Author>>(){}
        );

        assertEquals(2, authors.size());
        assertEquals("Brandon", authors.getFirst().getFirstName());
        assertEquals("Sanderson", authors.getFirst().getLastName());
    }

    @Test
    void getAuthorTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/authors/{id}", author2.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Author author = objectMapper.readValue(
                result.getResponse().getContentAsString(), Author.class
        );

        assertEquals(author2.getFirstName(), author.getFirstName());
        assertEquals(author2.getLastName(), author.getLastName());
    }

    @Test
    void addNewAuthorTest() throws Exception {
        Author fixture = new Author("Mariana", "Enriquez");
        String body = objectMapper.writeValueAsString(fixture);

        MvcResult result = mockMvc.perform(post("/authors").with(user("test").roles("LIBRARIAN"))
                    .content(body)
                    .contentType(MediaType.APPLICATION_JSON)
                )
                    .andExpect(status().isCreated())
                    .andReturn();
        Author author = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                Author.class
        );

        assertEquals("Mariana", author.getFirstName());
        assertEquals("Enriquez", author.getLastName());
    }

    @Test
    void updateAuthorTest() throws Exception {
        Author updatedAuthor = new Author("Brandon Winn", null);

        String updatedAuthorJson = objectMapper.writeValueAsString(updatedAuthor);

        MvcResult result = mockMvc.perform(patch("/authors/{id}", author1.getId()).with(user("test").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAuthorJson))
                .andExpect(status().isOk())
                .andReturn();

        Author author = objectMapper.readValue(
                result.getResponse().getContentAsString(), Author.class
        );

        assertEquals(author1.getId(), author.getId());
        assertEquals(updatedAuthor.getFirstName(), author.getFirstName());
    }

    @Test
    void deleteAuthorTest() throws Exception {
        mockMvc.perform(delete("/authors/{id}", author1.getId()).with(user("test").roles("ADMIN")))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/authors/{id}", author1.getId()))
                .andExpect(status().isNotFound());
    }

}