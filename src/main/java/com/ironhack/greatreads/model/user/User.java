package com.ironhack.greatreads.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Collection;
import java.util.ArrayList;

import static jakarta.persistence.FetchType.EAGER;

@Entity
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

    /**
     * The password used to log in
     */
    @NotEmpty(message = "You must provide a password")
    private String password;

    /**
     * The roles that the user has
     */
    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();


    public User(String name, String userName, String email, String password) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
