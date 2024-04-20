package com.ironhack.greatreads.controller;

import com.ironhack.greatreads.model.user.Librarian;
import com.ironhack.greatreads.repository.LibrarianRepository;
import com.ironhack.greatreads.service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibrarianController {

    @Autowired
    LibrarianService librarianService;

    @Autowired
    LibrarianRepository librarianRepository;

    @GetMapping("/librarians")
    @ResponseStatus(HttpStatus.OK)
    public List<Librarian> getAllLibrarians() { return librarianService.getAllLibrarians(); }

    @PostMapping("/librarians")
    @ResponseStatus(HttpStatus.CREATED)
    public Librarian addLibrarian(@RequestBody Librarian librarian) { return librarianRepository.save(librarian); }
}
