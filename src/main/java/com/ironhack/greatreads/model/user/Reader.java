package com.ironhack.greatreads.model.user;

import com.ironhack.greatreads.model.library.Library;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "readers")
@NoArgsConstructor
public class Reader extends User {


    @OneToOne(mappedBy = "reader")
    private Library library;


    public Reader(String name, String userName, String email) {
        super(name, userName, email);
    }
}
