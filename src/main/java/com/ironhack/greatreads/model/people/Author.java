package com.ironhack.greatreads.model.people;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.greatreads.model.book.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
