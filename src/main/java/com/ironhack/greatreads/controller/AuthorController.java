package com.ironhack.greatreads.controller;

import com.ironhack.greatreads.model.Author;
import com.ironhack.greatreads.repository.AuthorRepository;
import com.ironhack.greatreads.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @Autowired
    AuthorRepository authorRepository;

    @GetMapping("/authors")
    @ResponseStatus(HttpStatus.OK)
    public List<Author> getAllAuthors() { return authorService.getAllAuthors(); }

    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public Author addAuthor(@RequestBody Author author) { return authorRepository.save(author); }
}
