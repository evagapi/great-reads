package com.ironhack.greatreads.model.user;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Data
@NoArgsConstructor
public class User {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "You must provide a name")
    private String name;

    @NotEmpty(message = "You must provide a username")
    private String userName;

    @NotEmpty(message = "You must provide an email")
    private String email;

    public User(String name, String userName, String email) {
        this.name = name;
        this.userName = userName;
        this.email = email;
    }
}
