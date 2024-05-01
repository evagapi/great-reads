package com.ironhack.greatreads.service.user;

import com.ironhack.greatreads.model.user.Librarian;
import com.ironhack.greatreads.repository.user.LibrarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LibrarianService {

    @Autowired
    LibrarianRepository librarianRepository;

    public List<Librarian> getAllLibrarians() { return librarianRepository.findAll(); }

    public Librarian getLibrarianById(int id) {
        return librarianRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Librarian not found"));
    };

    public Librarian addNewLibrarian(Librarian librarian) { return librarianRepository.save(librarian); }

    public Librarian updateLibrarian(int id, Librarian librarian) {
        Librarian existingLibrarian = librarianRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Librarian not found"));

        if (librarian.getName() != null) existingLibrarian.setName(librarian.getName());
        if (librarian.getUserName() != null) existingLibrarian.setUserName(librarian.getUserName());
        if (librarian.getEmail() != null) existingLibrarian.setEmail(librarian.getEmail());

        librarianRepository.save(existingLibrarian);

        return existingLibrarian;
    }

    public void deleteLibrarian(int id) {
        Librarian existingLibrarian = librarianRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Librarian not found"));

        librarianRepository.delete(existingLibrarian);
    }
}
