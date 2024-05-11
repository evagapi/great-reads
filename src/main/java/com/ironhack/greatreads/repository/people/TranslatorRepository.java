package com.ironhack.greatreads.repository.people;

import com.ironhack.greatreads.model.people.Translator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranslatorRepository extends JpaRepository<Translator, Integer> {

    Optional<Translator> findByLastName(String lastName);
    Optional<Translator> findByFirstName(String firstName);
    Optional<Translator> findById(int id);

}
