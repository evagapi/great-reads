package com.ironhack.greatreads.repository.user;

import com.ironhack.greatreads.model.library.Library;
import com.ironhack.greatreads.model.user.User;
import com.ironhack.greatreads.repository.library.LibraryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LibraryRepository libraryRepository;

    User user;
    Library library;
    @BeforeEach
    void setUp() {
        user = new User("Matilda Wormwood", "littleusermatilda", "matilda.wormwood@example.com", "brucebruce");
        userRepository.save(user);

        library = new Library();
        libraryRepository.save(library);
    }

    @AfterEach
    void tearDown() {
        libraryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByIdTest() {
        Optional<User> $user = userRepository.findById(user.getId());
        assertTrue($user.isPresent());
        assertEquals(user.getId(), $user.get().getId());
    }

    @Test
    void findByNameTest() {
        Optional<User> $user = userRepository.findByName(user.getName());
        assertTrue($user.isPresent());
        assertEquals(user.getName(), $user.get().getName());
    }

    @Test
    void findByUsernameTest() {
        Optional<User> $user = userRepository.findByUsername(user.getUsername());
        assertTrue($user.isPresent());
        assertEquals(user.getUsername(),$user.get().getUsername());
    }

    @Test
    void findByEmailTest() {
        Optional<User> $user = userRepository.findByEmail(user.getEmail());
        assertTrue($user.isPresent());
        assertEquals(user.getEmail(), $user.get().getEmail());
    }
}