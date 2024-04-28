package com.ironhack.greatreads.controller.book;

import com.ironhack.greatreads.model.book.Author;
import com.ironhack.greatreads.service.book.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/authors")
    @ResponseStatus(HttpStatus.OK)
    public List<Author> getAllAuthors() { return authorService.getAllAuthors(); }

    @GetMapping("/authors/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Author getAuthorById(@PathVariable int id) { return authorService.getAuthorById(id); }

    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public Author addAuthor(@RequestBody Author author) { return authorService.addNewAuthor(author); }

    @PatchMapping("/authors/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Author updateAuthor(@PathVariable int id, @RequestBody Author author) {
        return authorService.updateAuthor(id, author);
    }

    @DeleteMapping("/authors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deteleAuthor(@PathVariable int id) {
        authorService.deleteAuthor(id);
    }
}
