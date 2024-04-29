package com.ironhack.greatreads.service.book;

import com.ironhack.greatreads.model.book.Book;
import com.ironhack.greatreads.model.book.Genre;
import com.ironhack.greatreads.repository.book.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    GenreRepository genreRepository;

    public List<Genre> getAllGenres() { return genreRepository.findAll(); }

    public Genre addNewGenre(Genre genre) { return genreRepository.save(genre); }

    public Genre getGenreById(int id) {
        return genreRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));
    }

    public Genre updateGenre(int id, Genre genre) {
        Genre existingGenre = genreRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));

        if (genre.getName() != null) existingGenre.setName(genre.getName());

        genreRepository.save(existingGenre);

        return existingGenre;
    }

    public void deleteGenre(int id) {
        Genre existingGenre = genreRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Genre not found"));

        List<Book> books = existingGenre.getBooks();

        if (!books.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete a genre with associated books");
        }

        genreRepository.delete(existingGenre);
    }
}
