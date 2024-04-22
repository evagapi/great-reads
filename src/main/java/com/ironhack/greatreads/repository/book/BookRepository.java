package com.ironhack.greatreads.repository.book;

import com.ironhack.greatreads.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findBookByTitle(String title);
    Optional<Book> findBookByAuthorLastName(String lastName);
    Optional<Book> findBookByGenreName(String genreName);

}
