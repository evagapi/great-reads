package com.ironhack.greatreads.model.library;

import com.ironhack.greatreads.model.Book;
import com.ironhack.greatreads.model.user.Reader;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "libraries")
public class Library {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;
}
