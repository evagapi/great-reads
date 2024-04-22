package com.ironhack.greatreads.repository.book;

import com.ironhack.greatreads.model.book.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByLastName(String lastName);
    Optional<Author> findByFirstName(String firstName);
    Optional<Author> findById(int id);
}
