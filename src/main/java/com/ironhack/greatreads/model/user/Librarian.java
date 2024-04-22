package com.ironhack.greatreads.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "librarians")
@NoArgsConstructor
public class Librarian extends User {

    public Librarian(String name, String userName, String email) {
        super(name, userName, email);
    }
}
