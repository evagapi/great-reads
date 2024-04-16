package com.ironhack.greatreads.service;

import com.ironhack.greatreads.model.Genre;
import com.ironhack.greatreads.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    GenreRepository genreRepository;

    public List<Genre> getAllGenres() { return genreRepository.findAll(); }

    public Genre addNewGenre(Genre genre) { return genreRepository.save(genre); }
}
