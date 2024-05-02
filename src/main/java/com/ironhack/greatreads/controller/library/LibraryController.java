package com.ironhack.greatreads.controller.library;

import com.ironhack.greatreads.model.library.Library;
import com.ironhack.greatreads.service.library.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @GetMapping("libraries")
    @ResponseStatus(HttpStatus.OK)
    public List<Library> getAllLibraries() { return libraryService.getAllLibraries(); }

    @GetMapping("libraries/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Library getLibraryById(@PathVariable int id) { return libraryService.getLibraryById(id); }

    @PostMapping("libraries")
    @ResponseStatus(HttpStatus.CREATED)
    public Library addLibrary(@RequestBody Library library) { return libraryService.addNewLibrary(library); }
}
