package com.ironhack.greatreads.service.library;

import com.ironhack.greatreads.model.library.Library;
import com.ironhack.greatreads.repository.library.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;

    public List<Library> getAllLibraries() { return libraryRepository.findAll(); }

    public Library getLibraryById(int id) {
        return libraryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Library not found"));
    }

    public Library addNewLibrary(Library library) { return libraryRepository.save(library); }
}
