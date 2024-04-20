package com.ironhack.greatreads.service.book;

import com.ironhack.greatreads.model.book.Genre;
import com.ironhack.greatreads.repository.book.GenreRepository;
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
