package com.ironhack.greatreads.repository.library;

import com.ironhack.greatreads.model.library.Library;
import com.ironhack.greatreads.model.user.User;
import com.ironhack.greatreads.repository.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryRepositoryTest {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private UserRepository readerRepository;

    private Library library;
    private User reader;
    @BeforeEach
    void setUp() {
        reader = new User("Ada Lovelace", "adalovelace", "ada.lovelace@gmail.com");
        readerRepository.save(reader);

        library = new Library();
        library.setUser(reader);
        libraryRepository.save(library);
    }

    @AfterEach
    void tearDown() {
        libraryRepository.deleteAll();
        readerRepository.deleteAll();
    }

    @Test
    void findByIdTest() {
        Optional<Library> $library = libraryRepository.findById(library.getId());
        assertTrue($library.isPresent());
        assertEquals(library.getId(), $library.get().getId());
    }
}