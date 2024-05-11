package com.ironhack.greatreads.repository.book;

import com.ironhack.greatreads.model.people.Author;
import com.ironhack.greatreads.model.book.Book;
import com.ironhack.greatreads.model.book.Genre;
import com.ironhack.greatreads.repository.people.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    AuthorRepository authorRepository;

    Genre genre;
    Author author;
    Book book;

    @BeforeEach
    void setUp() {
        genre = new Genre("Sci-fi");
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
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
        genreRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void findBookByTitleTest() {
        Optional<Book> $book = bookRepository.findByTitle("El imperio final");
        assertTrue($book.isPresent());
        assertEquals("El imperio final", $book.get().getTitle());
    }

    @Test
    void findBookByAuthorLastNameTest() {
        Optional<Book> $book = bookRepository.findByAuthorLastName(author.getLastName());
        assertTrue($book.isPresent());
        assertEquals("Sanderson", $book.get().getAuthor().getLastName());
    }

    @Test
    void findBookByGenreTest() {
        Optional<Book> $book = bookRepository.findByGenreName(genre.getName());
        assertTrue($book.isPresent());
        assertEquals("Sci-fi", $book.get().getGenre().getName());
    }

    @Test
    void deleteBookTest() {
        bookRepository.delete(book);
        Optional<Book> $book = bookRepository.findById(book.getId());
        assertFalse($book.isPresent());
    }
}