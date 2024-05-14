package com.ironhack.greatreads.controller.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.greatreads.controller.library.dto.BookStatusDTO;
import com.ironhack.greatreads.model.book.Book;
import com.ironhack.greatreads.model.book.Genre;
import com.ironhack.greatreads.model.library.Status;
import com.ironhack.greatreads.model.people.Author;
import com.ironhack.greatreads.model.user.Role;
import com.ironhack.greatreads.model.user.User;
import com.ironhack.greatreads.repository.book.BookRepository;
import com.ironhack.greatreads.repository.book.GenreRepository;
import com.ironhack.greatreads.repository.people.AuthorRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
class LibraryControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private User user1;
    private User user2;

    private Genre genre;
    private Author author;
    private Book book;

    @BeforeEach
    void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        genre = new Genre("Fantasy");
        author = new Author("Brandon", "Sanderson");
        book = new Book();
        book.setTitle("El imperio final");
        book.setNumberOfPages(672);
        book.setIsbn("9788417347291");
        book.setLanguage("Spanish");
        book.setPublisher("Nova");
        book.setGenre(genre);
        book.setAuthor(author);

        genreRepository.save(genre);
        authorRepository.save(author);
        bookRepository.save(book);

        user1 = new User("Matilda Wormwood", "matilda", "mwormwood@example.com", "brucebruce");
        user2 = new User("Catherine Morland", "kattym", "catherine.morland@example.com", "udolpho123");
        userService.saveRole(new Role(null, "ROLE_USER"));
        userService.createUser(user1, "ROLE_USER");
        userService.createUser(user2, "ROLE_USER");
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
        genreRepository.deleteAll();
        authorRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void addABookToLibraryTest() throws Exception {
        BookStatusDTO bookStatusDTO = new BookStatusDTO();
        bookStatusDTO.setBookId(book.getId());
        bookStatusDTO.setStatus(Status.WANT_TO_READ);

        MvcResult result = mockMvc.perform(post("/me/library").with(user("matilda"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookId\":" + book.getId() + ",\"status\":\"WANT_TO_READ\"}"))
                .andExpect(status().isOk())
                .andReturn();

        Optional<User> $user = userRepository.findByUsername("matilda");
        assertTrue($user.isPresent());
        User updatedUser = $user.get();
        assertNotNull(updatedUser);
        assertEquals(Status.WANT_TO_READ, updatedUser.getLibrary().getBookStatuses().getFirst().getStatus());
    }
}
