package com.ironhack.greatreads.repository.people;

import com.ironhack.greatreads.model.people.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByLastName(String lastName);
    Optional<Author> findByFirstName(String firstName);
    Optional<Author> findById(int id);
}
