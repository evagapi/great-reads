package com.ironhack.greatreads.controller.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.greatreads.model.book.Genre;
import com.ironhack.greatreads.repository.book.GenreRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class GenreControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GenreRepository genreRepository;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Genre genre1;
    private Genre genre2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        genre1 = new Genre("Sci-Fi");
        genre2 = new Genre("Fantasi");
        genreRepository.saveAll(List.of(genre1, genre2));
    }

    @AfterEach
    void tearDown() {
        genreRepository.deleteAll();
    }

    @Test
    void getAllGenresTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Genre> genres = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<Genre>>() {}
        );

        assertEquals(2, genres.size());
        assertEquals("Sci-Fi", genres.getFirst().getName());
    }

    @Test
    void addNewGenreTest() throws Exception {
        Genre fixture = new Genre("Comedy");
        String body = objectMapper.writeValueAsString(fixture);

        MvcResult result = mockMvc.perform(post("/genres")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();

        Genre genre = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                Genre.class
        );

        assertEquals("Comedy", genre.getName());
    }

    @Test
    void updateGenreTest() throws Exception {
        Genre updatedGenre = new Genre("Scifi");

        String updatedGenreJson = objectMapper.writeValueAsString(updatedGenre);

        MvcResult result = mockMvc.perform(patch("/genres/{id}", genre1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedGenreJson))
                .andExpect(status().isOk())
                .andReturn();

        Genre genre = objectMapper.readValue(
                result.getResponse().getContentAsString(), Genre.class
        );

        assertEquals(genre1.getId(), genre.getId());
        assertEquals(updatedGenre.getName(), genre.getName());
    }

    @Test
    void deleteGenreTest() throws Exception {
        mockMvc.perform(delete("/genres/{id}", genre1.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/genres/{id}", genre1.getId()))
                .andExpect(status().isNotFound());
    }
}