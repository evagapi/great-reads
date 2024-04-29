package com.ironhack.greatreads.controller.book;

import com.ironhack.greatreads.model.book.Genre;
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

    @GetMapping("/genres/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Genre getGenreById(@PathVariable int id) { return genreService.getGenreById(id); }

    @PostMapping("/genres")
    @ResponseStatus(HttpStatus.CREATED)
    public Genre addGenre(@RequestBody Genre genre) { return genreService.addNewGenre(genre); }

    @PatchMapping("/genres/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Genre updateGenre(@PathVariable int id, @RequestBody Genre genre) {
        return genreService.updateGenre(id, genre);
    }

    @DeleteMapping("/genres/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGenre(@PathVariable int id) {
        genreService.deleteGenre(id);
    }

}
