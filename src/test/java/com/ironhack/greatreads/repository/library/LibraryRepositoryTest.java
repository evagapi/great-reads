package com.ironhack.greatreads.repository.library;

import com.ironhack.greatreads.model.library.Library;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LibraryRepositoryTest {

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private ReaderRepository readerRepository;

    private Library library;
    private Reader reader;
    @BeforeEach
    void setUp() {
        reader = new Reader("Ada Lovelace", "adalovelace", "ada.lovelace@gmail.com");
        readerRepository.save(reader);

        library = new Library();
        library.setReader(reader);
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