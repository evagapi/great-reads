package com.ironhack.greatreads.service;

import com.ironhack.greatreads.model.Author;
import com.ironhack.greatreads.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public List<Author> getAllAuthors() { return authorRepository.findAll(); }

    public Author addNewAuthor(Author author) { return authorRepository.save(author); }
}
