package com.ironhack.greatreads.controller.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.greatreads.model.book.Book;
import com.ironhack.greatreads.model.book.Genre;
import com.ironhack.greatreads.model.people.Author;
import com.ironhack.greatreads.repository.book.BookRepository;
import com.ironhack.greatreads.repository.book.GenreRepository;
import com.ironhack.greatreads.repository.people.AuthorRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BookControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Book book1;
    private Book book2;

    Genre fantasyGenre;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        fantasyGenre = new Genre("Fantasy");
        Author author = new Author("Brandon", "Sanderson");
        book1 = new Book();
        book1.setTitle("El imperio final");
        book1.setNumberOfPages(672);
        book1.setIsbn("9788417347291");
        book1.setLanguage("Spanish");
        book1.setPublisher("Nova");
        book1.setGenre(fantasyGenre);
        book1.setAuthor(author);

        genreRepository.save(fantasyGenre);
        authorRepository.save(author);


        Genre genre2 = new Genre("Young Adult");
        Author author2 = new Author("J.K.", "Rowling");
        book2 = new Book();
        book2.setTitle("Harry Potter");
        book2.setNumberOfPages(487);
        book2.setIsbn("9780000000000");
        book2.setLanguage("English");
        book2.setPublisher("Whatever");
        book2.setGenre(genre2);
        book2.setAuthor(author2);

        genreRepository.save(genre2);
        authorRepository.save(author2);

        bookRepository.saveAll(List.of(book1, book2));
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    void getBooksTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/books").with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andReturn();

        List<Book> books = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Book>>() {});
        assertEquals(2, books.size());
        assertEquals(book1.getTitle(), books.get(0).getTitle());
        assertEquals(book2.getTitle(), books.get(1).getTitle());
    }

    @Test
    void addBookTest() throws Exception {
        Author author3 = new Author("J.R.R.", "Tolkien");
        Book fixture = new Book();
        fixture.setTitle("The Hobbit");
        fixture.setNumberOfPages(200); // You can set the number of pages accordingly
        fixture.setIsbn("9780000000000"); // You can set the ISBN accordingly
        fixture.setLanguage("English"); // You can set the language accordingly
        fixture.setPublisher("HarperCollins"); // You can set the publisher accordingly
        fixture.setGenre(fantasyGenre);
        fixture.setAuthor(author3);

        genreRepository.save(fantasyGenre);
        authorRepository.save(author3);
        String body = objectMapper.writeValueAsString(fixture);

        MvcResult result = mockMvc.perform(post("/books").with(user("test").roles("ADMIN"))
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Book book = objectMapper.readValue(result.getResponse().getContentAsString(), Book.class);
        assertEquals("The Hobbit", book.getTitle());
    }
}