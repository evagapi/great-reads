package com.ironhack.greatreads.service.library;

import com.ironhack.greatreads.model.library.Library;
import com.ironhack.greatreads.repository.library.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;

    public List<Library> getAllLibraries() { return libraryRepository.findAll(); }

    public Library addNewLibrary(Library library) { return libraryRepository.save(library); }
}
