package com.ironhack.greatreads.service.book;

import com.ironhack.greatreads.model.book.Author;
import com.ironhack.greatreads.model.book.Book;
import com.ironhack.greatreads.repository.book.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public List<Author> getAllAuthors() { return authorRepository.findAll(); }

    public Author getAuthorById(int id) {
        return authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
    }

    public Author addNewAuthor(Author author) { return authorRepository.save(author); }

    public void updateAuthor(int id, Author author) {
        Author existingAuthor = authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
        existingAuthor.setFirstName(author.getFirstName());
        existingAuthor.setLastName(author.getLastName());

        List<Book> newBooks = author.getBooks();

        if (newBooks != null ) {
            List<Book> existingBooks = existingAuthor.getBooks();

            for (Book newBook : newBooks) {
                if (!existingBooks.contains(newBook)) {
                    existingBooks.add(newBook);
                }
            }
            existingAuthor.setBooks(existingBooks);
        }

        authorRepository.save(existingAuthor);
    }

    public void deleteAuthor(int id) {
        Author existingAuthor = authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        List<Book> books = existingAuthor.getBooks();

        if (!books.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete an author with associated books");
        }

        authorRepository.delete(existingAuthor);
    }
}
