package com.ironhack.greatreads.controller.people;

import com.ironhack.greatreads.model.people.Translator;
import com.ironhack.greatreads.service.people.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class TranslatorController {

    @Autowired
    TranslatorService translatorService;

    @GetMapping("/translators")
    @ResponseStatus(HttpStatus.OK)
    public List<Translator> getAllTranslators() { return translatorService.getAllTranslators(); }

    @GetMapping("/translators/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Translator> getTranslatorById(@PathVariable int id) {
        try {
            Translator foundTranslator = translatorService.getTranslatorById(id);
            return ResponseEntity.status(HttpStatus.OK).body(foundTranslator);
        } catch (ResponseStatusException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PostMapping("/translators")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addTranslator(@RequestBody Translator translator) {
        try {
            Translator newTranslator = translatorService.addNewTranslator(translator);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTranslator);
        } catch (ResponseStatusException ex) {
            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PatchMapping("/translators/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Translator> updateTranslator(@PathVariable int id, @RequestBody Translator translator) {
        try {
            Translator updatedTranslator = translatorService.updateTranslator(id, translator);
            return ResponseEntity.status(HttpStatus.OK).body(updatedTranslator);
        } catch (ResponseStatusException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @DeleteMapping("/translators/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deteleTranslator(@PathVariable int id) {
        try {
            translatorService.deleteTranslator(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (ResponseStatusException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) return ResponseEntity.notFound().build();
            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
}
