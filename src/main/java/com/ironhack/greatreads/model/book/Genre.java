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
@Table(name = "genres")
@Data
@NoArgsConstructor
public class Genre {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany
    @JoinColumn(name = "genre_id")
    @JsonIgnore
    private List<Book> books = new ArrayList<>();

    public Genre(String name) {
        this.name = name;
    }

    public Genre(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }
}
