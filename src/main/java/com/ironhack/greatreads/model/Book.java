package com.ironhack.greatreads.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int numberOfPages;
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    @JsonIgnore
    private Genre genre;

    private String language;
    private String publisher;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private Author author;

    public Book(String title, int numberOfPages, String isbn, Genre genre, String language, String publisher, Author author) {
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.isbn = isbn;
        this.genre = genre;
        this.language = language;
        this.publisher = publisher;
        this.author = author;
    }
}
