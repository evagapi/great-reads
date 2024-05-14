package com.ironhack.greatreads.service.book;

import com.ironhack.greatreads.model.book.Book;
import com.ironhack.greatreads.model.book.Genre;
import com.ironhack.greatreads.model.people.Author;
import com.ironhack.greatreads.model.people.Translator;
import com.ironhack.greatreads.repository.book.BookRepository;
import com.ironhack.greatreads.repository.book.GenreRepository;
import com.ironhack.greatreads.repository.people.AuthorRepository;
import com.ironhack.greatreads.repository.people.TranslatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    GenreRepository genreRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    TranslatorRepository translatorRepository;

   public List<Book> getAllBooks() { return bookRepository.findAll(); }

    public Book getBookById(int id) {
        return bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    public Book addNewBook(Book book) {
       updateBookRelationships(book, book);
       return bookRepository.save(book);
   }

    public Book updateBook(int id, Book book) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        if (book.getTitle() != null) existingBook.setTitle(book.getTitle());
        if (book.getNumberOfPages() > 0) existingBook.setNumberOfPages(book.getNumberOfPages());
        if (book.getTitle() != null) existingBook.setIsbn(existingBook.getIsbn());
        if (book.getLanguage() != null) existingBook.setLanguage(book.getLanguage());
        if (book.getPublisher() != null) existingBook.setPublisher(book.getPublisher());

        //relationships
        updateBookRelationships(book, existingBook);

        bookRepository.save(existingBook);

        return existingBook;
    }

    private void updateBookRelationships(Book book, Book existingBook) {
        if (book.getGenre() != null) {
            int genreId = book.getGenre().getId();
            if (genreId > 0) {
                Optional<Genre> genre = genreRepository.findById(genreId);
                if (genre.isPresent()){
                    existingBook.setGenre(genre.get());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Genre not found");
                }
            }
        }
        if (book.getAuthor() != null) {
            int authorId = book.getAuthor().getId();
            if (authorId > 0) {
                Optional<Author> author = authorRepository.findById(authorId);
                if (author.isPresent()){
                    existingBook.setAuthor(author.get());
                } else {
                   throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author not found");
                }
            }
        }

        if (book.getTranslator() != null) {
            int translatorId = book.getTranslator().getId();
            if (translatorId > 0) {
                Optional<Translator> translator = translatorRepository.findById(translatorId);
                if (translator.isPresent()){
                    existingBook.setTranslator(translator.get());
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Translator not found");
                }
            }
        }
    }
}
