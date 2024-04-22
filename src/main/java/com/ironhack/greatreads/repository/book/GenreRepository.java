package com.ironhack.greatreads.repository.book;

import com.ironhack.greatreads.model.book.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Optional<Genre> findByName(String name);
}
