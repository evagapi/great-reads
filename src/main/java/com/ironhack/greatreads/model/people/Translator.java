package com.ironhack.greatreads.model.people;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.greatreads.model.book.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "translators")
@Data
@NoArgsConstructor
public class Translator extends Person {
    @OneToMany(mappedBy = "translator")
    @JsonIgnore
    private List<Book> translatedBooks = new ArrayList<>();
}
