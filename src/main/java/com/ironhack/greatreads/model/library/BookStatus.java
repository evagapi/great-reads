package com.ironhack.greatreads.model.library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ironhack.greatreads.model.book.Book;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@Table(name = "book_statuses")
public class BookStatus {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonIgnoreProperties("bookStatuses")
    private Book book;

    @Enumerated(EnumType.STRING)
    private Status status;
}
