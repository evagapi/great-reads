package com.ironhack.greatreads.service.library;

import com.ironhack.greatreads.model.library.BookStatus;
import com.ironhack.greatreads.repository.library.BookStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookStatusService {

    @Autowired
    BookStatusRepository bookStatusRepository;

    public List<BookStatus> getAllBookStatuses() { return bookStatusRepository.findAll(); }

    public BookStatus addNewBookStatus(BookStatus bookStatus) { return bookStatusRepository.save(bookStatus); }
}
