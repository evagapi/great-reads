package com.ironhack.greatreads.service.user;

import com.ironhack.greatreads.model.user.Reader;
import com.ironhack.greatreads.repository.user.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReaderService {

    @Autowired
    ReaderRepository readerRepository;

    public List<Reader> getAllReaders() { return readerRepository.findAll(); }

    public Reader getReaderById(int id) {
        return readerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reader not found"));
    }

    public Reader addNewReader(Reader reader) { return readerRepository.save(reader); }

    public Reader updateReader(int id, Reader reader) {
        Reader existingReader = readerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reader not found"));

        if (reader.getName() != null) existingReader.setName(reader.getName());
        if (reader.getUserName() != null) existingReader.setUserName(reader.getUserName());
        if (reader.getEmail() != null) existingReader.setEmail(reader.getEmail());

        readerRepository.save(existingReader);

        return existingReader;
    }

    public void deleteReader(int id) {
        Reader existingReader = readerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reader not found"));

        readerRepository.delete(existingReader);
    }
}
