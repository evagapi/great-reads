package com.ironhack.greatreads.model.user;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
public class User {

    private String name;
    private String userName;
    private String email;

    public User(String name, String userName, String email) {
        this.name = name;
        this.userName = userName;
        this.email = email;
    }
}
