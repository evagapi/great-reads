package com.ironhack.greatreads.repository;

import com.ironhack.greatreads.model.user.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {
}
