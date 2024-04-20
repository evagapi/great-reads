package com.ironhack.greatreads.controller.book;

import com.ironhack.greatreads.model.book.Author;
import com.ironhack.greatreads.repository.book.AuthorRepository;
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

    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public Author addAuthor(@RequestBody Author author) { return authorService.addNewAuthor(author); }
}
