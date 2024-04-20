package com.ironhack.greatreads.model.library;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.greatreads.model.Book;
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
    @JoinColumn(name = "library_id")
    @JsonIgnore
    private Library library;

    @ManyToOne
    @JsonIgnore
    private Book book;

    @Enumerated(EnumType.STRING)
    private Status status;

}
