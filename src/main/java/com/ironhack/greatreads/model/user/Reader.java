package com.ironhack.greatreads.model.user;

import com.ironhack.greatreads.model.library.Library;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Setter;

@Entity
@Table(name = "readers")
public class Reader extends User {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(mappedBy = "reader")
    private Library library;


    public Reader(String name, String userName, String email) {
        super(name, userName, email);
    }
}
