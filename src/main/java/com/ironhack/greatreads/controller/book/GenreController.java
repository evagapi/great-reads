package com.ironhack.greatreads.controller.book;

import com.ironhack.greatreads.model.book.Genre;
import com.ironhack.greatreads.repository.book.GenreRepository;
import com.ironhack.greatreads.service.book.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GenreController {
    @Autowired
    GenreService genreService;

    @GetMapping("/genres")
    @ResponseStatus(HttpStatus.OK)
    public List<Genre> getAllGenres() { return genreService.getAllGenres(); }

    @PostMapping("/genres")
    @ResponseStatus(HttpStatus.CREATED)
    public Genre addGenre(@RequestBody Genre genre) { return genreService.addNewGenre(genre); }

}
