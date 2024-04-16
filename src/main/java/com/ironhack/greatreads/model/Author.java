package com.ironhack.greatreads.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String secondName;

    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<Book> books;

    public Author(String firstName, String secondName, List<Book> books) {
        this.firstName = firstName;
        this.secondName = secondName;
    }
}
