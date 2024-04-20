package com.ironhack.greatreads.controller.library;

import com.ironhack.greatreads.model.library.BookStatus;
import com.ironhack.greatreads.service.library.BookStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookStatusController {

    @Autowired
    BookStatusService bookStatusService;

    @GetMapping("/bookstatuses")
    @ResponseStatus(HttpStatus.OK)
    public List<BookStatus> getAllBookStatuses() { return bookStatusService.getAllBookStatuses(); }

    @PostMapping("/bookstatuses")
    @ResponseStatus(HttpStatus.CREATED)
    public BookStatus addBookStatus(@RequestBody BookStatus bookStatus) { return bookStatusService.addNewBookStatus(bookStatus); }

}
