package com.ironhack.greatreads.service;

import com.ironhack.greatreads.model.user.Librarian;
import com.ironhack.greatreads.repository.LibrarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrarianService {

    @Autowired
    LibrarianRepository librarianRepository;

    public List<Librarian> getAllLibrarians() { return librarianRepository.findAll(); }

    public Librarian addNewLibrarian(Librarian librarian) { return librarianRepository.save(librarian); }
}
