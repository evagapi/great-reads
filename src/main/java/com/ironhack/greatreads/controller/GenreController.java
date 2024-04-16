package com.ironhack.greatreads.controller;

import com.ironhack.greatreads.model.Genre;
import com.ironhack.greatreads.repository.GenreRepository;
import com.ironhack.greatreads.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GenreController {
    @Autowired
    GenreService genreService;

    @Autowired
    GenreRepository genreRepository;

    @GetMapping("/genres")
    @ResponseStatus(HttpStatus.OK)
    public List<Genre> getAllGenres() { return genreService.getAllGenres(); }

    @PostMapping("/genres")
    @ResponseStatus(HttpStatus.CREATED)
    public Genre addGenre(@RequestBody Genre genre) { return genreRepository.save(genre); }

}
