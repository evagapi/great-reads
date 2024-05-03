package com.ironhack.greatreads.model.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.greatreads.model.library.BookStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "You must provide a title")
    private String title;

    @Positive(message = "You must provide a number of pages")
    private int numberOfPages;

    @NotEmpty(message = "You must provide an isbn")
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @NotEmpty(message = "You must provide a language")
    private String language;

    @NotEmpty(message = "You must provide a publisher")
    private String publisher;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<BookStatus> bookStatuses = new ArrayList<>();
}
