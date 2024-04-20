package com.ironhack.greatreads.controller;

import com.ironhack.greatreads.model.user.Reader;
import com.ironhack.greatreads.repository.ReaderRepository;
import com.ironhack.greatreads.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReaderController {

    @Autowired
    ReaderService readerService;

    @Autowired
    ReaderRepository readerRepository;

    @GetMapping("/readers")
    @ResponseStatus(HttpStatus.OK)
    public List<Reader> getAllReaders() { return readerService.getAllReaders(); }

    @PostMapping("readers")
    @ResponseStatus(HttpStatus.CREATED)
    public Reader addReader(@RequestBody Reader reader) { return readerRepository.save(reader); }

}
