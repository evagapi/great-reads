package com.ironhack.greatreads.repository.user;

import com.ironhack.greatreads.model.user.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {
    Optional<Reader> findById(int id);
    Optional<Reader> findByName(String name);
    Optional<Reader> findByUserName(String userName);

    Optional<Reader> findByEmail(String email);
}
