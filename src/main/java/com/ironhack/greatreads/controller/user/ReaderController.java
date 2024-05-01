package com.ironhack.greatreads.controller.user;

import com.ironhack.greatreads.model.user.Reader;
import com.ironhack.greatreads.service.user.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReaderController {

    @Autowired
    ReaderService readerService;

    @GetMapping("/readers")
    @ResponseStatus(HttpStatus.OK)
    public List<Reader> getAllReaders() { return readerService.getAllReaders(); }

    @GetMapping("/readers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Reader getReaderById(@PathVariable int id) { return readerService.getReaderById(id); }

    @PostMapping("readers")
    @ResponseStatus(HttpStatus.CREATED)
    public Reader addReader(@RequestBody Reader reader) { return readerService.addNewReader(reader); }

    @PatchMapping("/readers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Reader updateReader(@PathVariable int id, @RequestBody Reader reader) {
        return readerService.updateReader(id, reader);
    }

    @DeleteMapping("/readers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReader(@PathVariable int id) { readerService.deleteReader(id); }

}
