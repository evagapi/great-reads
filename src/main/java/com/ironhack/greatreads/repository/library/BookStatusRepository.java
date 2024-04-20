package com.ironhack.greatreads.repository.library;

import com.ironhack.greatreads.model.library.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStatusRepository extends JpaRepository<BookStatus, Integer> {
}
