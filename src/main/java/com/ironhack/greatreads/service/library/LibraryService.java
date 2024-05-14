package com.ironhack.greatreads.service.library;

import com.ironhack.greatreads.controller.library.dto.BookStatusDTO;
import com.ironhack.greatreads.model.book.Book;
import com.ironhack.greatreads.model.library.BookStatus;
import com.ironhack.greatreads.model.library.Library;
import com.ironhack.greatreads.model.user.User;
import com.ironhack.greatreads.repository.book.BookRepository;
import com.ironhack.greatreads.repository.library.BookStatusRepository;
import com.ironhack.greatreads.repository.library.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    BookStatusRepository bookStatusRepository;


    @Autowired
    BookRepository bookRepository;

    public void addBookStatusToLibraryFromUser(User user, BookStatusDTO bookStatusDTO) {
        Library library = user.getLibrary();

        List<BookStatus> bookStatuses = library.getBookStatuses();
        Optional<Book> $book = bookRepository.findById(bookStatusDTO.getBookId());
        if ($book.isPresent()) {
            boolean found = false;
            for (BookStatus existingBookStatus : bookStatuses) {
                if (existingBookStatus.getBook().getId() == bookStatusDTO.getBookId()) {
                    existingBookStatus.setStatus(bookStatusDTO.getStatus());
                    bookStatusRepository.save(existingBookStatus);
                    found = true;
                    break;
                }
            }
            if (!found) {
                BookStatus bookStatus = new BookStatus();
                bookStatus.setBook($book.get());
                bookStatus.setStatus(bookStatusDTO.getStatus());
                bookStatusRepository.save(bookStatus);
                bookStatuses.add(bookStatus);
            }
            libraryRepository.save(library);
        }
    }
}
