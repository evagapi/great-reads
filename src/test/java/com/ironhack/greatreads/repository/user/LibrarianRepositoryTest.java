package com.ironhack.greatreads.repository.user;

import com.ironhack.greatreads.model.user.Librarian;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LibrarianRepositoryTest {

    @Autowired
    LibrarianRepository librarianRepository;

    Librarian librarian;

    @BeforeEach
    void setUp() {
        librarian = new Librarian("Madam Pince", "madam_pince", "m.pince@hogwarts.edu");
        librarianRepository.save(librarian);
    }

    @AfterEach
    void tearDown() {
        librarianRepository.deleteAll();
    }

    @Test
    void findByIdTest() {
        Optional<Librarian> $librarian = librarianRepository.findById(librarian.getId());
        assertTrue($librarian.isPresent());
        assertEquals(librarian.getId(), $librarian.get().getId());
    }

    @Test
    void findByUserNameTest() {
        Optional<Librarian> $librarian = librarianRepository.findByUserName(librarian.getUserName());
        assertTrue($librarian.isPresent());
        assertEquals(librarian.getUserName(), $librarian.get().getUserName());
    }
}