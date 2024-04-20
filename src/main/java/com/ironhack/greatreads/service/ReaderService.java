package com.ironhack.greatreads.service;

import com.ironhack.greatreads.model.user.Reader;
import com.ironhack.greatreads.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {

    @Autowired
    ReaderRepository readerRepository;

    public List<Reader> getAllReaders() { return readerRepository.findAll(); }

    public Reader addNewReader(Reader reader) { return readerRepository.save(reader); }
}
