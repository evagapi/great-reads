package com.ironhack.greatreads.repository.user;

import com.ironhack.greatreads.model.library.Library;
import com.ironhack.greatreads.model.user.Reader;
import com.ironhack.greatreads.repository.library.LibraryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReaderRepositoryTest {

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    LibraryRepository libraryRepository;

    Reader reader;
    Library library;
    @BeforeEach
    void setUp() {
        reader = new Reader("Matilda Wormwood", "littlereadermatilda", "matilda.wormwood@example.com");
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
        Optional<Reader> $reader = readerRepository.findById(reader.getId());
        assertTrue($reader.isPresent());
        assertEquals(reader.getId(), $reader.get().getId());
    }

    @Test
    void findByNameTest() {
        Optional<Reader> $reader = readerRepository.findByName(reader.getName());
        assertTrue($reader.isPresent());
        assertEquals(reader.getName(), $reader.get().getName());
    }

    @Test
    void findByUserNameTest() {
        Optional<Reader> $reader = readerRepository.findByUserName(reader.getUserName());
        assertTrue($reader.isPresent());
        assertEquals(reader.getUserName(), $reader.get().getUserName());
    }

    @Test
    void findByEmailTest() {
        Optional<Reader> $reader = readerRepository.findByEmail(reader.getEmail());
        assertTrue($reader.isPresent());
        assertEquals(reader.getEmail(), $reader.get().getEmail());
    }
}