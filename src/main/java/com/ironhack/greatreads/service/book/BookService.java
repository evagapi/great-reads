package com.ironhack.greatreads.service.book;

import com.ironhack.greatreads.model.book.Book;
import com.ironhack.greatreads.repository.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> getAllBooks() { return bookRepository.findAll(); }

    public Book addNewBook(Book book) { return bookRepository.save(book); }
}