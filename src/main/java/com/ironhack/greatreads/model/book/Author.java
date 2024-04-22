package com.ironhack.greatreads.model.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class Author {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Author(String firstName, String lastName, List<Book> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }
}
