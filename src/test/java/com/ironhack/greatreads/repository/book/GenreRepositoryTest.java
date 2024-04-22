package com.ironhack.greatreads.repository.book;

import com.ironhack.greatreads.model.book.Book;
import com.ironhack.greatreads.model.book.Genre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GenreRepositoryTest {

    @Autowired
    GenreRepository genreRepository;

    Genre genre01;
    Genre genre02;

    @BeforeEach
    void setUp() {
        genre01 = new Genre("Sci-fi");
        genre02 = new Genre("Literary Fiction");
        genreRepository.save(genre01);
        genreRepository.save(genre02);
    }

    @AfterEach
    void tearDown() {
        genreRepository.deleteAll();
        genreRepository.flush();
    }

    @Test
    void getAllGenresTest() {
        List<Genre> genres = genreRepository.findAll();
        assertEquals(2, genres.size());
        assertEquals(genres.getFirst().getName(), genre01.getName());
    }

    @Test
    void findGenreByNameTest() {
        Optional<Genre> $genre = genreRepository.findGenreByName("Sci-fi");
        assertTrue($genre.isPresent());
        assertEquals("Sci-fi", $genre.get().getName());
    }

    @Test
    void deleteGenreTest() {
        genreRepository.delete(genre01);
        Optional<Genre> $genre = genreRepository.findById(genre01.getId());
        assertFalse($genre.isPresent());
    }

}