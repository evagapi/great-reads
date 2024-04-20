package com.ironhack.greatreads.model.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "librarians")
@NoArgsConstructor
public class Librarian extends User {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Librarian(String name, String userName, String email) {
        super(name, userName, email);
    }
}
