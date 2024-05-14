package com.ironhack.greatreads.service.people;

import com.ironhack.greatreads.model.book.Book;
import com.ironhack.greatreads.model.people.Author;
import com.ironhack.greatreads.repository.people.AuthorRepository;
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

    public Author updateAuthor(int id, Author author) {
        Author existingAuthor = authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        if (author.getFirstName() != null) existingAuthor.setFirstName(author.getFirstName());
        if (author.getLastName() != null) existingAuthor.setLastName(author.getLastName());

        authorRepository.save(existingAuthor);

        return existingAuthor;
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
