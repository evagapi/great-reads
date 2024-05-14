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
@Table(name = "authors")
@Data
@NoArgsConstructor
public class Author extends Person {

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }
}
