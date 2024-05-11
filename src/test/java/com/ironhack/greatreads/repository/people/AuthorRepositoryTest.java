package com.ironhack.greatreads.repository.people;

import com.ironhack.greatreads.model.people.Author;
import com.ironhack.greatreads.repository.people.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("Jane", "Austen");
        authorRepository.save(author);
    }

    @AfterEach
    void tearDown() {
        authorRepository.deleteAll();
    }

    @Test
    void findByLastNameTest() {
        Optional<Author> $author = authorRepository.findByLastName("Austen");
        assertTrue($author.isPresent());
        assertEquals("Austen", $author.get().getLastName());
    }

    @Test
    void findByFirstNameTest() {
        Optional<Author> $author = authorRepository.findByFirstName("Jane");
        assertTrue($author.isPresent());
        assertEquals("Jane", $author.get().getFirstName());
    }

    @Test
    void findByIdTest() {
        Optional<Author> $author = authorRepository.findById(author.getId());
        assertTrue($author.isPresent());
        assertEquals(author.getId(), $author.get().getId());
    }

    @Test
    void deleteAuthorTest() {
        authorRepository.delete(author);
        Optional<Author> $author = authorRepository.findById(author.getId());
        assertFalse($author.isPresent());
    }
}