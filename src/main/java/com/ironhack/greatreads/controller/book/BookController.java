package com.ironhack.greatreads.controller.book;

import com.ironhack.greatreads.model.book.Book;
import com.ironhack.greatreads.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAllBooks() { return bookService.getAllBooks(); }

    @GetMapping("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book getBookById(@PathVariable int id) { return bookService.getBookById(id); }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody Book book) { return bookService.addNewBook(book); }

    @PatchMapping("/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(@PathVariable int id, @RequestBody Book incompleteBook) {
        return bookService.updateBook(id, incompleteBook);
    }

}
