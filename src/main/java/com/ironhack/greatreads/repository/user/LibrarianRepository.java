package com.ironhack.greatreads.repository.user;

import com.ironhack.greatreads.model.user.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Integer> {
    Optional<Librarian> findById(int id);
    Optional<Librarian> findByUserName(String userName);
}
