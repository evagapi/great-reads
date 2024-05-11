package com.ironhack.greatreads.service.people;

import com.ironhack.greatreads.model.book.Book;
import com.ironhack.greatreads.model.people.Translator;
import com.ironhack.greatreads.repository.people.TranslatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TranslatorService {

    @Autowired
    TranslatorRepository translatorRepository;

    public List<Translator> getAllTranslators() { return translatorRepository.findAll(); }

    public Translator addNewTranslator(Translator translator) {
        if (translator.getFirstName() != null && translator.getLastName() != null) {
            return translatorRepository.save(translator);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name and last name cannot be empty");
        }

    }

    public Translator getTranslatorById(int id) {
        return translatorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Translator not found"));
    }

    public Translator updateTranslator(int id, Translator translator) {
        Translator existingTranslator = translatorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Translator not found"));

        if (translator.getFirstName() != null) existingTranslator.setFirstName(translator.getFirstName());
        if (translator.getLastName() != null) existingTranslator.setLastName(translator.getLastName());

        translatorRepository.save(existingTranslator);

        return existingTranslator;
    }

    public void deleteTranslator(int id) {
        Translator existingTranslator = translatorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Translator not found"));

        List<Book> translatedBooks = existingTranslator.getTranslatedBooks();

        if (!translatedBooks.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete a translator with associated translated books");
        }

        translatorRepository.delete(existingTranslator);
    }
}
