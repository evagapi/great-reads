package com.ironhack.greatreads.controller.book;

import com.ironhack.greatreads.model.book.Book;
import com.ironhack.greatreads.repository.book.BookRepository;
import com.ironhack.greatreads.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks() { return bookService.getAllBooks(); }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody Book book) { return bookRepository.save(book); }

}
