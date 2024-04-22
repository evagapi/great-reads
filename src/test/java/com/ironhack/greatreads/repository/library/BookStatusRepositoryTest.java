package com.ironhack.greatreads.repository.library;

import com.ironhack.greatreads.model.book.Author;
import com.ironhack.greatreads.model.book.Book;
import com.ironhack.greatreads.model.book.Genre;
import com.ironhack.greatreads.model.library.BookStatus;
import com.ironhack.greatreads.model.library.Library;
import com.ironhack.greatreads.model.library.Status;
import com.ironhack.greatreads.repository.book.AuthorRepository;
import com.ironhack.greatreads.repository.book.BookRepository;
import com.ironhack.greatreads.repository.book.GenreRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookStatusRepositoryTest {

    @Autowired
    BookStatusRepository bookStatusRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    AuthorRepository authorRepository;

    BookStatus bookStatus;
    Library library;
    Book book;
    Genre genre;
    Author author;

    @BeforeEach
    void setUp() {
        genre = new Genre("Sci-fi");
        author = new Author("Brandon", "Sanderson");

        authorRepository.save(author);
        genreRepository.save(genre);

        book = new Book();
        book.setTitle("El imperio final");
        book.setNumberOfPages(672);
        book.setIsbn("9788417347291");
        book.setLanguage("Spanish");
        book.setPublisher("Nova");
        book.setGenre(genre);
        book.setAuthor(author);

        bookRepository.save(book);

        library = new Library();
        libraryRepository.save(library);

        bookStatus = new BookStatus();
        bookStatus.setLibrary(library);
        bookStatus.setBook(book);
        bookStatus.setStatus(Status.WANT_TO_READ);

        bookStatusRepository.save(bookStatus);
    }

    @AfterEach
    void tearDown() {
        bookStatusRepository.deleteAll();
        libraryRepository.deleteAll();
        bookRepository.deleteAll();
        bookRepository.deleteAll();
        genreRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void findByIdTest() {
        Optional<BookStatus> $bookStatus = bookStatusRepository.findById(bookStatus.getId());
        assertTrue($bookStatus.isPresent());
        assertEquals(bookStatus.getId(), $bookStatus.get().getId());
    }

    @Test
    void deleteBookStatusTest() {
        bookStatusRepository.delete(bookStatus);
        Optional<BookStatus> $bookStatus = bookStatusRepository.findById(bookStatus.getId());
        assertFalse($bookStatus.isPresent());
    }

}