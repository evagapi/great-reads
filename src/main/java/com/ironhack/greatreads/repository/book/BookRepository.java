package com.ironhack.greatreads.repository.book;

import com.ironhack.greatreads.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByTitle(String title);
    Optional<Book> findByAuthorLastName(String lastName);
    Optional<Book> findByGenreName(String genreName);
}
